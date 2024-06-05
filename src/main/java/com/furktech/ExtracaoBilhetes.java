package com.furktech;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.furktech.automation.TelaAutenticacao2FA;
import com.furktech.automation.TelaBspPortal;
import com.furktech.automation.TelaIATAS;
import com.furktech.automation.TelaLogin;
import com.furktech.http.Requisicoes;
import com.furktech.model.User;
import com.furktech.utils.DecodeTOTP;
import com.furktech.utils.RequisicoesUtils;
import com.furktech.utils.WebDriverConfig;

import io.github.cdimascio.dotenv.Dotenv;

public class ExtracaoBilhetes {

    private static final Logger logger = LoggerFactory.getLogger(ExtracaoBilhetes.class);
    static final Dotenv dotenv = Dotenv.load();

    public static void realizaProcessamento(String agenda) throws IOException, InterruptedException {

        logger.info("Iniciando processo de extração de bilhetes para a AGENDA {}", agenda);

        WebDriver webdriver = WebDriverConfig.startaDriver();
        WebDriverWait wait = WebDriverConfig.configuraWait(webdriver,
                Duration.ofSeconds(Long.parseLong(dotenv.get("DEFAULT_WAIT_TIMEOUT"))),
                Duration.ofMillis(Long.parseLong(dotenv.get("DEFAULT_WAIT_POLLING"))));

        HttpResponse<String> dadosProcessamento = Requisicoes.getDadosProcessamento(agenda);

        User user = RequisicoesUtils.retornaUser(dadosProcessamento);
        RequisicoesUtils.retornaSolicitacoes(dadosProcessamento);

        TelaLogin.realizaLogin(webdriver, wait, user);

        String TOTPCode = DecodeTOTP.decodeTOTP(dotenv.get("OTPAUTH_MIGRATION_URL"));

        TelaAutenticacao2FA.insereTOTPCode(TOTPCode, wait);

        TelaBspPortal.goToIataService(webdriver, wait);

        TelaIATAS.iteraIatasComSolicitacoes(webdriver, wait, dadosProcessamento);

        webdriver.quit();

        logger.info("Processo de extração de bilhetes finalizado para a agenda {}", agenda);
    }
}
