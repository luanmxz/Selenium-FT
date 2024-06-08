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
            configuraPathGeckoDriver(args);
            return instanciaWebDriver(args[2]);
        } catch (Exception e) {
            logger.error("Message error -> {}", e.getMessage());
        }

        logger.error("Erro: Não foi possível instancia o webdriver!");
        return null;
    }

    private static void configuraPathGeckoDriver(String[] args) {
        String geckoDriverPath;
        if (args.length > 3) {
            logger.info("Recebendo PATH do webdriver como argumento!. PATH Recebido: {}", args[3]);
            geckoDriverPath = args[3];
        } else {
            geckoDriverPath = dotenv.get("PATH_GECKODRIVER", "/snap/bin/geckodriver");
            logger.info("Buscando PATH default do geckodriver. PATH {}", geckoDriverPath);
        }
        System.setProperty("webdriver.gecko.driver", geckoDriverPath);
    }

    private static WebDriver instanciaWebDriver(String modoHeadless) {
        if (Boolean.parseBoolean(modoHeadless) || "TRUE".equalsIgnoreCase(modoHeadless)) {
            logger.info("Executando FIREFOX no modo HEADLESS.");

            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("-headless");
            options.setLogLevel(FirefoxDriverLogLevel.TRACE);

            WebDriver webdriverHeadless = new FirefoxDriver(options);
            logger.info("Webdriver headless criado com sucesso!");
            return webdriverHeadless;
        } else {
            WebDriver webdriver = new FirefoxDriver();
            if (webdriver != null) {
                logger.info("Criou o webdriver com sucesso!");
            }
            return webdriver;
        }
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
