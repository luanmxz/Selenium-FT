package com.tech.furk.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaixaArquivos {

    private static final Logger logger = LoggerFactory.getLogger(BaixaArquivos.class);

    public static void realizaProcessamento(List<String> args) throws IOException, InterruptedException {

        logger.info("Iniciando processo de baixa de arquvios para a AGENDA {}", args.get(0));
    }

}
