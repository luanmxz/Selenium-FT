package com.furktech.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelaAutenticacao2FA {

    static final Logger logger = LoggerFactory.getLogger(TelaAutenticacao2FA.class);

    public static void insereTOTPCode(String totpcode, WebDriverWait wait) {

        logger.info("TOTP Code recebido: {}", totpcode);

        WebElement twoFaCodeInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "/html/body/div[1]/div/c-portal-custom-m-f-a-container/c-portal-login-container/div/div/div[1]/div[2]/slot/span/div/c-portal-enter2-fa-code/div[2]/c-portal-login-card-container/div/div/slot[4]/div/c-portal-mfa-activation-code/div[1]/div/div/div/input[1]")));

        twoFaCodeInput.sendKeys(totpcode);

        WebElement twoFAbutton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "/html/body/div[1]/div/c-portal-custom-m-f-a-container/c-portal-login-container/div/div/div[1]/div[2]/slot/span/div/c-portal-enter2-fa-code/div[2]/c-portal-login-card-container/div/div/slot[4]/div/div[3]/button")));

        twoFAbutton.click();
    }
}
