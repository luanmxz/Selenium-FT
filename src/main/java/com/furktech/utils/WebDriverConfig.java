package com.furktech.utils;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.cdimascio.dotenv.Dotenv;

public class WebDriverConfig {

    static final Logger logger = LoggerFactory.getLogger(WebDriverConfig.class);
    static final Dotenv dotenv = Dotenv.load();

    public static WebDriver startaDriver(String[] args) {

        try {
            if (args.length > 3) {
                logger.info("Recebendo PATH do webdriver como argumento!. PATH Recebido: {}",
                        args[3]);
                System.setProperty("webdriver.gecko.driver", args[3]);
            } else {
                logger.info("Buscando PATH default do geckodriver. PATH {}",
                        dotenv.get("PATH_GECKODRIVER"));
                System.setProperty("webdriver.gecko.driver", dotenv.get("PATH_GECKODRIVER"));
            }

            if (args[2].equals("TRUE") || Boolean.parseBoolean(args[2])) {
                logger.info("Executando FIREFOX no modo HEADLESS.");

                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("-headless");
                options.setLogLevel(FirefoxDriverLogLevel.TRACE);

                WebDriver webdriverHeadless = new FirefoxDriver(options);
                return webdriverHeadless;
            }

            WebDriver webdriver = new FirefoxDriver();

            if (webdriver != null)
                logger.info("Criou o webdriver com sucesso!");

            return webdriver;
        } catch (Exception e) {
            logger.error("Message error -> {}", e.getMessage());
        }

        logger.error("Erro: Não foi possível instancia o webdriver!");
        return null;

    }

    /*
     * Criando um Wait para aguardar o elemento na tela por X segundos, verifica a
     * condição do elemento a cada X milisegundos.
     */
    public static WebDriverWait configuraWait(WebDriver webdriver, Duration timeout, Duration pollingDuration) {

        Logger logger = LoggerFactory.getLogger(WebDriverConfig.class);

        logger.info("WebDriverWait configurado com timeout de {} segundos e pollingDuration de {} milissegundos",
                timeout, pollingDuration.toMillis());

        WebDriverWait wait = new WebDriverWait(webdriver, timeout, pollingDuration);
        return wait;
    }
}
