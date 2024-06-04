package com.furktech.selenium;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.furktech.utils.RequisicoesUtils;

public class BspPortal {

    static final Logger logger = LoggerFactory.getLogger(BspPortal.class);

    public static void goToIataService(WebDriver webdriver, WebDriverWait wait) {

        wait.until(ExpectedConditions
                .elementToBeClickable(By.cssSelector(".services-buttons > div:nth-child(1) > div:nth-child(1)")));

        webdriver.navigate().to("https://portal.iata.org/s/services?tab=myServices");
    }

    public static void extraiConteudoBilhete(WebDriverWait wait, WebElement tr, Entry<String, List<JsonNode>> entry) {

        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("/html/body/bspl-root/bspl-core/div/bspl-menu/nav/ul/li[3]")));

        // ITERAR AS SOLICITACOES PRESENTES NO ENTRY PASSANDO O NUM BILHETE DE CADA UMA
        // AO SCRIPT DO CLÓVIS
    }

    public static void iteraIatas(WebDriver webdriver, WebDriverWait wait,
            HttpResponse<String> dadosProcessamento) throws JsonMappingException, JsonProcessingException {

        // Recupera as solicitações separadas por unidade operacional
        Map<String, List<JsonNode>> solicitacoes = RequisicoesUtils.retornaSolicitacoes(dadosProcessamento);

        for (Map.Entry<String, List<JsonNode>> entry : solicitacoes.entrySet()) {

            webdriver.navigate().to("https://portal.iata.org/idp/login?app=0spw0000000blJn");

            WebElement tableIatas = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/center/table/tbody/tr/td[2]/form/table/tbody/tr[2]/td/div[1]/table")));

            String unidadeOperacional = entry.getKey().substring(0, entry.getKey().length() - 1);

            List<WebElement> trs = tableIatas.findElements(By.tagName("tr"));
            List<WebElement> trsToClick = new ArrayList<WebElement>();

            trs.forEach(tr -> {
                if (tr.findElements(By.tagName("td")).get(2).getText().trim().equals(unidadeOperacional)) {

                    trsToClick.add(tr);
                }
            });

            trsToClick.get(0).findElements(By.tagName("td")).get(0).findElement(By.tagName("input")).click();

            logger.info("Realizando processo para a IATA {}.",
                    trsToClick.get(0).findElements(By.tagName("td")).get(2).getText());

            WebElement submitButton = wait
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#iata_portal_entry")));

            submitButton.click();

            // Passa as solicitações do IATA atual para o processo de extração do bilhete.
            extraiConteudoBilhete(wait, trsToClick.get(0), entry);

            trsToClick.clear();
        }
    }
}