package com.tech.furk.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolicitaReembolso {

    private static final Logger logger = LoggerFactory.getLogger(SolicitaReembolso.class);

    public static void realizaProcessamento(List<String> args) throws IOException, InterruptedException {

        logger.info("Iniciando processo de pedido de reembolso para a AGENDA {}", args.get(0));
    }

}
