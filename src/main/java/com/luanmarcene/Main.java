package com.luanmarcene;

import java.io.UnsupportedEncodingException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println("Iniciando automação com Selenium!");

        // Criando uma nova instância do webdriver
        System.setProperty("webdriver.gecko.driver", "C:\\RPA\\Webdrivers\\geckodriver-v0.34.0-win64\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();

        /*
         * Criando um Wait para aguardar o elemento na tela por 30 segundos, verifica a
         * condição do elemento a cada 500 milisegundos.
         */
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30), Duration.ofMillis(500));

        Login.realizaLogin(driver, wait);
        Authenticator2FA.buscaInsere2FaCode(wait);

        // Printa o nome da janela aberta no momento.
        System.out.println("Título da página acessada: " + driver.getTitle());

        // driver.quit();

    }
}