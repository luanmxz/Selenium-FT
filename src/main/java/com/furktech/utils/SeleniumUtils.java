package com.furktech.utils;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class SeleniumUtils {

    public static void takeScreenshot(WebDriver webdriver, String pathToSaveScreenshot) throws IOException {
        File screenshot = ((TakesScreenshot) webdriver).getScreenshotAs(OutputType.FILE);
        FileHandler.copy(screenshot,
                Utils.createFile(pathToSaveScreenshot + "\\Error-Screenshot-" + Utils.getDataAtualString() + ".png"));
    }

}
