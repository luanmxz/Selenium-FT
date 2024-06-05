package com.furktech.automation;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.furktech.model.Solicitacao;

public class TelaBspLink {

    static final Logger logger = LoggerFactory.getLogger(TelaBspLink.class);

    public static void extraiBilhetes(WebDriverWait wait, WebElement tr, List<Solicitacao> solicitacoes) {

        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("/html/body/bspl-root/bspl-core/div/bspl-menu/nav/ul/li[3]")));

        // ITERAR AS SOLICITACOES PRESENTES NO ENTRY PASSANDO O NUM BILHETE DE CADA UMA
        // AO SCRIPT DO CLÓVIS
    }
}
