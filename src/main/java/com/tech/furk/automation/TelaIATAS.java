package com.tech.furk.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.tech.furk.model.SolicitacoesPorUnidadeOperacional;
import com.tech.furk.utils.RequisicoesUtils;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelaIATAS {

        static final Logger logger = LoggerFactory.getLogger(TelaIATAS.class);

        public static void iteraIatasComSolicitacoes(WebDriver webdriver, WebDriverWait wait,
                        HttpResponse<String> dadosProcessamento, List<String> args)
                        throws JsonMappingException, JsonProcessingException {

                logger.info("Iterando IATAS que possuem solicitações.");

                // Recupera as solicitações separadas por unidade operacional
                List<SolicitacoesPorUnidadeOperacional> solicitacoesPorUnidadeOperacional = RequisicoesUtils
                                .getSolicitacoesPorIATAFromHttpResponse(dadosProcessamento);

                for (SolicitacoesPorUnidadeOperacional solicitacoes : solicitacoesPorUnidadeOperacional) {
                        webdriver.navigate().to("https://portal.iata.org/idp/login?app=0spw0000000blJn");

                        WebElement tableIatas = wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("/html/body/center/table/tbody/tr/td[2]/form/table/tbody/tr[2]/td/div[1]/table")));

                        List<WebElement> trs = tableIatas.findElements(By.tagName("tr"));
                        List<WebElement> trsToClick = new ArrayList<WebElement>();

                        trs.forEach(tr -> {
                                if (tr.findElements(By.tagName("td")).get(2).getText().trim()
                                                .equals(solicitacoes.getUnidadeOperacional())) {

                                        trsToClick.add(tr);
                                }
                        });

                        trsToClick.get(0).findElements(By.tagName("td")).get(0).findElement(By.tagName("input"))
                                        .click();

                        logger.info("Realizando processo para a IATA {}.",
                                        trsToClick.get(0).findElements(By.tagName("td")).get(2).getText());

                        WebElement submitButton = wait
                                        .until(ExpectedConditions
                                                        .elementToBeClickable(By.cssSelector("#iata_portal_entry")));

                        submitButton.click();

                        // Extrai os bilhetes através do script js
                        TelaBspLink.executaJavascriptNaPagina(webdriver, wait, args);

                        trsToClick.clear();

                }
        }

        public static void iteraTodasIatas(WebDriver webdriver, WebDriverWait wait, List<String> args) {

                logger.info("Iterando todas as IATAS.");

                WebElement tableIatas = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("/html/body/center/table/tbody/tr/td[2]/form/table/tbody/tr[2]/td/div[1]/table")));

                List<WebElement> trs = tableIatas.findElements(By.tagName("tr"));

                trs.forEach(tr -> {
                        webdriver.navigate().to("https://portal.iata.org/idp/login?app=0spw0000000blJn");

                        tr.findElements(By.tagName("td")).get(0).findElement(By.tagName("input"))
                                        .click();

                        logger.info("Realizando processo para a IATA {}.",
                                        tr.findElements(By.tagName("td")).get(2).getText());

                        WebElement submitButton = wait.until(
                                        ExpectedConditions.elementToBeClickable(By.cssSelector("#iata_portal_entry")));

                        submitButton.click();

                        // Extrai os bilhetes através do script js
                        TelaBspLink.executaJavascriptNaPagina(webdriver, wait, args);

                });

        }
}
