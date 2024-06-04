package com.luanmarcene.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BspPortal {

    public static void goToIataService(WebDriver webdriver, WebDriverWait wait) {

        WebElement servicesAnchor = wait.until(ExpectedConditions
                .elementToBeClickable(By.cssSelector(".services-buttons > div:nth-child(1) > div:nth-child(1)")));

        servicesAnchor.click();

        WebElement servicesTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "/html/body/div[6]/div[1]/div/div/c-portal-header/div/div[1]/c-portal-page-container/div/div/slot/span/div/div[3]/div/div[1]/div/div[3]/c-portal-page-container/div/div/slot/span/div/div/div[1]/div[1]/div[1]")));

        servicesTab.click();

        WebElement bspLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "/html/body/div[6]/div[2]/div/div[2]/div/div/c-portal-services-page/c-portal-page-container/div/div/slot/span/div/div/div/c-portal-services-my-services/c-portal-services-access-granted/div/div[5]/c-portal-services-access-granted-card/c-portal-services-card-container/div/div[2]/div/div[3]/div[2]/slot/span/lightning-button/button")));

        JavascriptExecutor js = (JavascriptExecutor) webdriver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        bspLink.click();

        WebElement tableIATAfirst = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                "#firstDiv > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > input:nth-child(1)")));

        tableIATAfirst.click();

    }

}
