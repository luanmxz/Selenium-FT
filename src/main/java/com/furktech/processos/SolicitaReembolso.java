package com.furktech.processos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolicitaReembolso {

    private static final Logger logger = LoggerFactory.getLogger(SolicitaReembolso.class);

    public static void realizaProcessamento(String agenda, String[] args) {

        logger.info("Iniciando processo de pedido de reembolso para a AGENDA {}", agenda);
    }

}
