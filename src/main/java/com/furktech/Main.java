package com.furktech;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.furktech.processos.BaixaArquivos;
import com.furktech.processos.ExtracaoBilhetes;
import com.furktech.processos.SolicitaReembolso;
import com.furktech.utils.TiposProcessoEnum;

/**
 * @author luan.costa@wises.com.br
 * @version 1.0
 */

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException, InterruptedException {

        String agenda = args[0];

        try {
            switch (TiposProcessoEnum.valueOf(args[1])) {
                case EXTRACAO_BILHETES -> ExtracaoBilhetes.realizaProcessamento(agenda, args);
                case BAIXA_ARQUIVOS -> BaixaArquivos.realizaProcessamento(agenda, args);
                case SOLICITA_REEMBOLSO -> SolicitaReembolso.realizaProcessamento(agenda, args);
            }
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            logger.error("Erro: O segundo argumento (TIPO_PROCESSAMENTO) deve ser um dos seguintes valores: {}.",
                    java.util.Arrays.toString(TiposProcessoEnum.values()));
            System.exit(1);
        }
    }
}