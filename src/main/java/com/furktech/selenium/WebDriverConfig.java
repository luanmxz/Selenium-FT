package com.furktech.selenium;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDriverConfig {

    public static WebDriver startaDriver() {
        Logger logger = LoggerFactory.getLogger(WebDriverConfig.class);

        // Criando uma nova instância do webdriver
        System.setProperty("webdriver.gecko.driver", "C:\\RPA\\webdriver\\geckodriver-v0.34.0-win64\\geckodriver.exe");
        WebDriver webdriver = new FirefoxDriver();

        logger.info("Criando nova instância do WebDriver {}", webdriver.getClass().getName());

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
