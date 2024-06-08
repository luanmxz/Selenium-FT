package com.furktech.processos;

import java.io.IOException;
import java.io.File;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.furktech.automation.TelaAutenticacao2FA;
import com.furktech.automation.TelaBspPortal;
import com.furktech.automation.TelaIATAS;
import com.furktech.automation.TelaLogin;
import com.furktech.config.AutenticatorConfig;
import com.furktech.config.WebDriverConfig;
import com.furktech.http.Requisicoes;
import com.furktech.model.User;
import com.furktech.utils.RequisicoesUtils;
import com.furktech.utils.ErrorHandler;

import io.github.cdimascio.dotenv.Dotenv;

public class ExtracaoBilhetes {

    private static final Logger logger = LoggerFactory.getLogger(ExtracaoBilhetes.class);
    static final Dotenv dotenv = Dotenv.load();

    /**
     * Realiza o processo de extração de bilhetes no portal BSP para a Agenda
     * recebida.
     * Faz o login, realiza a autenticação 2 fatores, entra no BSPLink, itera as
     * IATAS a extrair os bilhetes e realiza a extração.
     * 
     * @param agenda
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void realizaProcessamento(String agenda, String[] args) throws IOException, InterruptedException {

        logger.info("Iniciando processo de extração de bilhetes para a AGENDA {}", agenda);

        WebDriver webdriver = WebDriverConfig.startaDriver(args);

        ErrorHandler.executeWithHandling(webdriver, (WebDriver d) -> {

            WebDriverWait wait = WebDriverConfig.configuraWait(d,
                    Duration.ofSeconds(Long.parseLong(dotenv.get("DEFAULT_WAIT_TIMEOUT", "60"))),
                    Duration.ofMillis(Long.parseLong(dotenv.get("DEFAULT_WAIT_POLLING", "500"))));

            HttpResponse<String> dadosProcessamento = Requisicoes.getDadosProcessamento(agenda);

            User user = RequisicoesUtils.getUserFromHttpResponse(dadosProcessamento);
            RequisicoesUtils.getSolicitacoesPorIATAFromHttpResponse(dadosProcessamento);

            TelaLogin.realizaLogin(d, wait, user);

            String TOTPCode = AutenticatorConfig.decodeTOTP(dotenv.get("OTPAUTH_MIGRATION_URL"));

            TelaAutenticacao2FA.insereTOTPCode(TOTPCode, wait);

            TelaBspPortal.goToIataService(d, wait);

            TelaIATAS.iteraIatasComSolicitacoes(d, wait, dadosProcessamento);

            d.quit();

            logger.info("Processo de extração de bilhetes finalizado para a agenda {}", agenda);
        });
    }

}
