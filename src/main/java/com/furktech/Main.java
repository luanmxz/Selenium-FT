package com.furktech;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.furktech.http.Requisicoes;
import com.furktech.model.User;
import com.furktech.selenium.Authenticator2FA;
import com.furktech.selenium.BspPortal;
import com.furktech.selenium.Login;
import com.furktech.selenium.WebDriverConfig;
import com.furktech.utils.DecodeTOTP;
import com.furktech.utils.RequisicoesUtils;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException, InterruptedException {

        if (args.length != 1) {
            System.err.println("Erro: java Main Argumento <AGENDA> não recebido.");
            System.exit(1);
        }

        String agenda = args[0];

        logger.info("Iniciando automação com Selenium para a AGENDA {}", agenda);

        WebDriver webdriver = WebDriverConfig.startaDriver();
        WebDriverWait wait = WebDriverConfig.configuraWait(webdriver, Duration.ofSeconds(60), Duration.ofMillis(500));

        HttpResponse<String> dadosProcessamento = Requisicoes.getDadosProcessamento(agenda);

        User user = RequisicoesUtils.retornaUser(dadosProcessamento);
        RequisicoesUtils.retornaSolicitacoes(dadosProcessamento);

        Login.realizaLogin(webdriver, wait, user);

        String TOTPCode = DecodeTOTP.decodeTOTP(
                "otpauth-migration://offline?data=CiEKFIIlhwIICQW0fKZaGfu9shlhx89ZEgNCU1AgASgBMAIQARgBIAAopf7KmP7%2F%2F%2F%2F%2FAQ%3D%3D");

        Authenticator2FA.insereTOTPCode(TOTPCode, wait);

        BspPortal.goToIataService(webdriver, wait);

        BspPortal.iteraIatas(webdriver, wait, dadosProcessamento);

        webdriver.quit();

        logger.info("Processo de extração de bilhetes finalizado para a agenda {}", agenda);
    }
}