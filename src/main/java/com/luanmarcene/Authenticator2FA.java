package com.luanmarcene;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base32;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Authenticator;
import com.google.protobuf.InvalidProtocolBufferException;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Authenticator2FA {

    public static void buscaInsere2FaCode(WebDriverWait wait) throws UnsupportedEncodingException {

        String migrationUrl = "otpauth-migration://offline?data=CiEKFIIlhwIICQW0fKZaGfu9shlhx89ZEgNCU1AgASgBMAIQARgBIAAopf7KmP7%2F%2F%2F%2F%2FAQ%3D%3D";

        List<Entry<String, String>> parsedQuery = parseQuery(migrationUrl);
        byte[] decodedData = Base64.getDecoder().decode(parsedQuery.get(0).getValue());

        String secretCode = "";
        String faCode = "";

        try {
            Authenticator.MigrationPayload payload = Authenticator.MigrationPayload.parseFrom(decodedData);
            for (Authenticator.MigrationPayload.OtpParameters otpParameters : payload.getOtpParametersList()) {
                System.out.println("----------");
                System.out.println("Account Name: " + otpParameters.getName());
                System.out.println("Issuer: " + otpParameters.getIssuer());
                System.out.println("Secret: " + otpParameters.getSecret());
                System.out.println("Type: " + otpParameters.getType());
                System.out.println("Algorithm: " + otpParameters.getAlgorithm());
                System.out.println("Digits: " + otpParameters.getDigits());
                System.out.println("Counter: " + otpParameters.getCounter());

                Base32 base32 = new Base32();
                secretCode = base32.encodeToString(otpParameters.getSecret().toByteArray()).replace("=", "");
                System.out.println("Secret code decoded = " + secretCode);

                faCode = getTOTPCode(secretCode);
                System.out.println("faCode decoded = " + faCode);
                System.out.println("----------");

            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        WebElement twoFaCodeInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "/html/body/div[1]/div/c-portal-custom-m-f-a-container/c-portal-login-container/div/div/div[1]/div[2]/slot/span/div/c-portal-enter2-fa-code/div[2]/c-portal-login-card-container/div/div/slot[4]/div/c-portal-mfa-activation-code/div[1]/div/div/div/input[1]")));

        twoFaCodeInput.sendKeys(faCode);

        WebElement twoFAbutton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "/html/body/div[1]/div/c-portal-custom-m-f-a-container/c-portal-login-container/div/div/div[1]/div[2]/slot/span/div/c-portal-enter2-fa-code/div[2]/c-portal-login-card-container/div/div/slot[4]/div/div[3]/button")));

        twoFAbutton.click();
    }

    public static List<Entry<String, String>> parseQuery(String query) throws UnsupportedEncodingException {
        List<Entry<String, String>> queryPairs = new ArrayList<>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
            String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
            queryPairs.add(new SimpleEntry<>(key, value));
        }
        return queryPairs;
    }

    public static String getTOTPCode(String secretKey) {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = new GoogleAuthenticatorKey.Builder(secretKey).build();
        return String.valueOf(gAuth.getTotpPassword(key.getKey()));
    }
}
