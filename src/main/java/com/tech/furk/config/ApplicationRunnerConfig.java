package com.tech.furk.config;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

import com.tech.furk.model.TiposProcessoEnum;
import com.tech.furk.service.BaixaArquivos;
import com.tech.furk.service.ExtracaoBilhetes;
import com.tech.furk.service.SolicitaReembolso;

@Component
public class ApplicationRunnerConfig implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationRunnerConfig.class);

    @Autowired
    private AsyncTaskExecutor taskExecutor;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> nonOptionArgs = args.getNonOptionArgs();

        if (nonOptionArgs.size() >= 3) {
            String processo = nonOptionArgs.get(1);
            try {
                TiposProcessoEnum tipoProcesso = TiposProcessoEnum.valueOf(processo);
                executarProcesso(tipoProcesso, nonOptionArgs);
            } catch (IllegalArgumentException e) {
                logger.error("Erro: O segundo argumento (TIPO_PROCESSAMENTO) deve ser um dos seguintes valores: {}.",
                        java.util.Arrays.toString(TiposProcessoEnum.values()));
                System.exit(1);
            }
        }
    }

    private void executarProcesso(TiposProcessoEnum tipoProcesso, List<String> args) {
        Future<?> future = null;
        try {
            switch (tipoProcesso) {
                case EXTRACAO_BILHETES:
                    future = taskExecutor.submit(() -> executarExtracaoBilhetes(args));
                    break;
                case BAIXA_ARQUIVOS:
                    future = taskExecutor.submit(() -> executarBaixaArquivos(args));
                    break;
                case SOLICITA_REEMBOLSO:
                    future = taskExecutor.submit(() -> executarSolicitaReembolso(args));
                    break;
                default:
                    logger.error("Tipo de processo não suportado: {}", tipoProcesso);
                    System.exit(1);
            }

            if (future != null) {
                future.get();
            }
        } catch (Exception e) {
            logger.error("Erro ao executar o processo {}: {}", tipoProcesso, e.getMessage(), e);
        }
    }

    private void executarExtracaoBilhetes(List<String> args) {
        logThreadName();
        try {
            ExtracaoBilhetes.realizaProcessamento(args);
        } catch (IOException | InterruptedException e) {
            logger.error("Erro na extração de bilhetes: {}", e.getMessage(), e);
        }
    }

    private void executarBaixaArquivos(List<String> args) {
        logThreadName();
        try {
            BaixaArquivos.realizaProcessamento(args);
        } catch (IOException | InterruptedException e) {
            logger.error("Erro na baixa de arquivos: {}", e.getMessage(), e);
        }
    }

    private void executarSolicitaReembolso(List<String> args) {
        logThreadName();
        try {
            SolicitaReembolso.realizaProcessamento(args);
        } catch (IOException | InterruptedException e) {
            logger.error("Erro na solicitação de reembolso: {}", e.getMessage(), e);
        }
    }

    private void logThreadName() {
        logger.info("======================== NOME DA THREAD ================================");
        logger.info(Thread.currentThread().toString());
        logger.info("========================================================================");
    }
}
