package com.furktech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolicitaReembolso {

    private static final Logger logger = LoggerFactory.getLogger(SolicitaReembolso.class);

    public static void realizaProcessamento(String agenda) {

        logger.info("Iniciando processo de pedido de reembolso para a AGENDA {}", agenda);
    }

}
