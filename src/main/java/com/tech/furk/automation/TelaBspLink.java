package com.tech.furk.automation;

import java.io.FileNotFoundException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tech.furk.model.TiposProcessoEnum;
import com.tech.furk.utils.JavascriptUtils;

import io.github.cdimascio.dotenv.Dotenv;

public class TelaBspLink {

        static final Logger logger = LoggerFactory.getLogger(TelaBspLink.class);
        static final Dotenv dotenv = Dotenv.load();

        public static void executaJavascriptNaPagina(WebDriver webdriver, WebDriverWait wait, List<String> args) {

                wait.until(ExpectedConditions
                                .elementToBeClickable(
                                                By.xpath("/html/body/bspl-root/bspl-core/div/bspl-menu/nav/ul/li[3]")));

                JavascriptExecutor js = (JavascriptExecutor) webdriver;

                String jsContent = "";

                try {
                        jsContent = args.get(4);
                } catch (IndexOutOfBoundsException indexOutOfBoundsException) {

                        try {
                                switch (TiposProcessoEnum.valueOf(args.get(1))) {
                                        case TiposProcessoEnum.EXTRACAO_BILHETES -> jsContent = JavascriptUtils
                                                        .readSingleJavascriptFile(
                                                                        dotenv.get("PATH_JS_EXTRAI_BILHETES"));
                                        case TiposProcessoEnum.BAIXA_ARQUIVOS -> jsContent = JavascriptUtils
                                                        .readSingleJavascriptFile(
                                                                        dotenv.get("PATH_JS_BAIXA_ARQUIVOS"));
                                        case TiposProcessoEnum.SOLICITA_REEMBOLSO -> jsContent = JavascriptUtils
                                                        .readSingleJavascriptFile(
                                                                        dotenv.get("PATH_JS_SOLICITA_REEMBOLSO"));
                                }

                        } catch (FileNotFoundException e) {
                                logger.error(e.getMessage());
                        }
                }

                js.executeScript(jsContent);
        }
}
