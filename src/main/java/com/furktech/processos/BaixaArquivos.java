package com.furktech.processos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaixaArquivos {

    private static final Logger logger = LoggerFactory.getLogger(BaixaArquivos.class);

    public static void realizaProcessamento(String agenda, String[] args) {

        logger.info("Iniciando processo de baixa de arquvios para a AGENDA {}", agenda);
    }

}
