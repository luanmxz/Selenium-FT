package com.tech.furk.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelaBspPortal {

    static final Logger logger = LoggerFactory.getLogger(TelaBspPortal.class);

    public static void goToIataService(WebDriver webdriver, WebDriverWait wait) {

        wait.until(ExpectedConditions
                .elementToBeClickable(By.cssSelector(".services-buttons > div:nth-child(1) > div:nth-child(1)")));

        webdriver.navigate().to("https://portal.iata.org/s/services?tab=myServices");
    }

}