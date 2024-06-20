package com.tech.furk.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tech.furk.model.User;

public class TelaLogin {

        static final Logger logger = LoggerFactory.getLogger(TelaLogin.class);

        public static void realizaLogin(WebDriver driver, WebDriverWait wait, User user) {

                driver.get("https://portal.iata.org/s/login/?language=pt_BR");

                logger.info("Entrou na tela do portal COM SUCESSO!.");

                driver.manage().window().maximize();

                // Busca os elementos atráves do x-path respeitando o wait.
                WebElement inputLogin = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                                "/html/body/div[6]/div/div/div[2]/div/div/c-portal-login/c-portal-login-container/div/div/div[1]/div[2]/slot/span/div/div/c-portal-login-card-container/div/div/slot[3]/span/div/div/div[2]/div/input")));
                WebElement inputSenha = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                                "/html/body/div[6]/div/div/div[2]/div/div/c-portal-login/c-portal-login-container/div/div/div[1]/div[2]/slot/span/div/div/c-portal-login-card-container/div/div/slot[3]/span/div/div/div[4]/div[1]/input")));

                // Inputa os valores nos elementos.
                inputLogin.sendKeys(user.getUsername());
                inputSenha.sendKeys(user.getSenha());

                WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                                "/html/body/div[6]/div/div/div[2]/div/div/c-portal-login/c-portal-login-container/div/div/div[1]/div[2]/slot/span/div/div/c-portal-login-card-container/div/div/slot[3]/span/div/div/button")));
                loginButton.click();

                logger.info("Username inputado: {}", inputLogin.getAttribute("value"));
                logger.info("Senha inputada: {}", inputSenha.getAttribute("value"));
        }

}
