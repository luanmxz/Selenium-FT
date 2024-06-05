package com.furktech.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base32;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.Authenticator;
import com.google.protobuf.InvalidProtocolBufferException;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

public class DecodeTOTP {

    static final Logger logger = LoggerFactory.getLogger(DecodeTOTP.class);

    // Decodifica o QR CODE URL e retorna a secret key
    public static String decodeTOTP(String googleMigrationUrl) throws UnsupportedEncodingException {
        String migrationUrl = googleMigrationUrl;
        List<Entry<String, String>> parsedUrl = parseUrl(migrationUrl);
        byte[] decodedData = Base64.getDecoder().decode(parsedUrl.get(0).getValue());

        try {
            Authenticator.MigrationPayload payload = Authenticator.MigrationPayload.parseFrom(decodedData);
            String secretKey = extractSecretKey(payload);

            String TOTPCode = getTOTPCode(secretKey);

            while (TOTPCode.length() != 6) {
                logger.info("TOTPCode gerado inválido {}, gerando novo código.", TOTPCode);

                TOTPCode = getTOTPCode(secretKey);

                logger.info("Novo TOTPCode Gerado", TOTPCode);
            }

            return TOTPCode;
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            throw new RuntimeException("Falha no decode do TOTP Url", e);
        }
    }

    // Realiza o parse da URL e retorna uma lista de key-value pairs
    public static List<Entry<String, String>> parseUrl(String url) throws UnsupportedEncodingException {
        List<Entry<String, String>> urlPairs = new ArrayList<>();
        String[] pairs = url.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
            String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
            urlPairs.add(new SimpleEntry<>(key, value));
        }
        return urlPairs;
    }

    // Extrai a secret key do TOTP payload
    private static String extractSecretKey(Authenticator.MigrationPayload payload) {

        for (Authenticator.MigrationPayload.OtpParameters otpParameters : payload.getOtpParametersList()) {
            logger.info("---------- PAYLOAD INFO -----------");
            logger.info("Account name: {}", otpParameters.getName());
            logger.info("Issuer: {}", otpParameters.getIssuer());
            logger.info("Secret: {}", otpParameters.getSecret());
            logger.info("Type: {}", otpParameters.getType());
            logger.info("Algorithm: {}", otpParameters.getAlgorithm());
            logger.info("Digits: {}", otpParameters.getDigits());
            logger.info("Counter: {}", otpParameters.getCounter());
            logger.info("-----------------------------------");

            Base32 base32 = new Base32();
            return base32.encodeToString(otpParameters.getSecret().toByteArray()).replace("=", "");
        }
        throw new RuntimeException("Nenhuma secret key encontrada no TOTP payload");
    }

    // Gera um código TOTP a partir da secret key
    public static String getTOTPCode(String secretKey) {

        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = new GoogleAuthenticatorKey.Builder(secretKey).build();

        String TOTPCode = String.valueOf(gAuth.getTotpPassword(key.getKey()));

        logger.info("TOTP Code Gerado: {}", TOTPCode);
        return TOTPCode;
    }
}
