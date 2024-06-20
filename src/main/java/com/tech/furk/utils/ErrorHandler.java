package com.tech.furk.utils;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.cdimascio.dotenv.Dotenv;

public class ErrorHandler {

    private static final String DEFAULT_LOG_ERROR_DIR = Utils.getRootDirectory() + "RPA/Logs/Error/";

    static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
    static final Dotenv dotenv = Dotenv.load();

    /**
     * Loga o erro e tira print da tela no momento do erro e salva no caminho
     * definido.
     * 
     * @param webdriver          Instância do Selenium WebDriver
     * @param webDriverException Exceção do Selenium WebDriver que foi lançada
     * @throws IOException
     */
    public static void webDriverErrorHandler(WebDriver webdriver, WebDriverException webDriverException)
            throws IOException {

        String pathToSaveLogError = dotenv.get("PATH_SCREENSHOTS",
                Utils.createDirectoryIfNotExists(DEFAULT_LOG_ERROR_DIR).toString());

        SeleniumUtils.saveLogError(webdriver, webDriverException, pathToSaveLogError);
    }

    /**
     * Executa a ação recebida como parâmetro, e caso haja um erro
     * relacionado ao webdriver, trata esse erro com a classe
     * WebDriverErrorHandler. Se não for uma WebDriverException,
     * loga o erro normalmente.
     * 
     * @param webdriver Instância do Selenium WebDriver.
     * @param action    Metódo que será executado.
     */
    public static void executeWithHandling(WebDriver webdriver, WebDriverAction action) {
        try {
            action.execute(webdriver);
        } catch (WebDriverException webDriverException) {
            try {
                logger.error("Erro no Selenium WebDriver. ERRO => {}", webDriverException.getMessage());
                webDriverErrorHandler(webdriver, webDriverException);
            } catch (IOException ioException) {
                logger.error("Erro ao salvar a captura de tela: {}", ioException.getMessage());
            }
        } catch (Exception exception) {
            logger.error("Ocorreu um erro na aplicação: {}", exception.getMessage());
        }
    }

    @FunctionalInterface
    public interface WebDriverAction {
        void execute(WebDriver driver) throws Exception;
    }
}
