package com.furktech.utils;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.cdimascio.dotenv.Dotenv;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverConfig {

    static final Logger logger = LoggerFactory.getLogger(WebDriverConfig.class);
    static final Dotenv dotenv = Dotenv.load();

    public static WebDriver startaDriver() {

        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-headless");

        WebDriver webdriver = new FirefoxDriver(options);

        // Criando uma nova instância do webdriver
        /*
         * System.setProperty("webdriver.gecko.driver",
         * dotenv.get("FIREFOX_WEBDRIVER_PATH"));
         * 
         * FirefoxOptions options = new FirefoxOptions();
         * options.addArguments("-headless");
         * 
         * WebDriver webdriver = new FirefoxDriver(options);
         * 
         * logger.info("Criando nova instância do WebDriver {}",
         * webdriver.getClass().getName());
         */

        return webdriver;
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
