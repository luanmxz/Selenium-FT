package com.tech.furk.utils;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavascriptUtils {

    private static final Logger logger = LoggerFactory.getLogger(JavascriptUtils.class);

    public static String readSingleJavascriptFile(String jsFilePath) throws FileNotFoundException {

        File jsFile = new File(jsFilePath);

        if (!jsFile.getName().endsWith(".js")) {
            logger.error("It's not a JavaScript file");
            return null;
        }

        StringBuilder contentJS = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(jsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentJS.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            logger.error("An error occurred while reading the file: " + e.getMessage());
        }

        return contentJS.toString();
    }

    public static List<String> getAndSortJavascriptFilesFromDir(String dirPath) throws IOException {
        logger.info("Reading Directory containg the JS files");

        List<String> listFiles = new ArrayList<String>();

        try (Stream<Path> paths = Files.walk(Paths.get(dirPath))) {
            listFiles = paths.filter(Files::isRegularFile).map(Path::toString).filter(name -> name.endsWith(".js"))
                    .sorted().collect(Collectors.toList());
        } catch (NoSuchFileException e) {
            throw new IOException("The specified directory does not exist: " + dirPath, e);
        } catch (SecurityException e) {
            throw new IOException("Access denied to the specified directory: " + dirPath, e);
        } catch (IOException e) {
            throw new IOException("An I/O error occurred while accessing the directory: " + dirPath, e);
        }

        return listFiles;
    }

}
