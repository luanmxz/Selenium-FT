package com.furktech.utils;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.io.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeleniumUtils {

    static final Logger logger = LoggerFactory.getLogger(SeleniumUtils.class);

    public static File takeScreenshot(WebDriver webdriver, String pathToSaveScreenshot) throws IOException {

        logger.info("Tirando screenshot da tela no momento do erro");

        File screenshot = ((TakesScreenshot) webdriver).getScreenshotAs(OutputType.FILE);

        return screenshot;

    }

    public static void saveLogError(WebDriver webdriver, WebDriverException webDriverException,
            String pathToSaveLogError) throws IOException {

        File screenshot = takeScreenshot(webdriver, pathToSaveLogError);

        String data = Utils.getDataAtualString();
        String dataTruncate = Utils.getDataAtualTruncateString();

        String dirLogErro = Utils.createDirectoryIfNotExists(pathToSaveLogError + "\\" + dataTruncate)
                .toString();

        String pathScreenshot = dirLogErro + "\\" + data + "_"
                + "Error-Screenshot.png";

        FileHandler.copy(screenshot,
                Utils.createFile(pathScreenshot));

        File errorLogFile = Utils.createFile(dirLogErro + "\\" + data + "-Error_log.txt");
        FileWriter writer = new FileWriter(errorLogFile);

        try {
            writer.write("Mensagem do erro: " + webDriverException.getMessage());
            writer.write("================================================");
            writer.write("Stack trace: " + webDriverException.getStackTrace());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            writer.close();
        }

        logger.error(webDriverException.getMessage());
        logger.info("Log e screenshot salvos em {}", dirLogErro);

    }

}
