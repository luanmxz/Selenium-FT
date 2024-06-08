package com.furktech.utils;

import java.io.IOException;
import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static String getRunningOS() {
        String os = System.getProperty("os.name").toLowerCase();
        return os;
    }

    public static String getRootDirectory() {
        String os = getRunningOS();
        if (os.contains("win")) {
            return System.getenv("SystemDrive");
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            // Retorna o diretório raiz no caso de Unix/Linux
            return "/";
        } else {
            // Retorno padrão para outros sistemas operacionais *Não implementado*
            return "";
        }
    }

    /**
     * Cria um diretório caso ele não existir.
     * 
     * @param path Caminho do diretório a ser buscado/criado.
     * @return Caminho do diretório buscado/criado.
     */
    public static Path createDirectoryIfNotExists(String path) {
        Path directory = Paths.get(path);
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
                logger.info("Diretório {} criado na raíz da máquina.", directory);
                return directory;
            } catch (IOException e) {
                System.err.println("Erro ao criar diretório: " + e.getMessage());
            }
        }
        return directory;
    }

    public static File createFile(String fileName) {
        File file = new File(fileName);
        return file;
    }

    public static String getDataAtualString() {
        DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String data = dataFormatada.format(LocalDateTime.now());

        return data;
    }
}
