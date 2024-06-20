package com.tech.furk.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tech.furk.model.ArgsExecution;
import com.tech.furk.model.TiposProcessoEnum;
import com.tech.furk.service.BaixaArquivos;
import com.tech.furk.service.ExtracaoBilhetes;
import com.tech.furk.service.SolicitaReembolso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Future;

import javax.script.ScriptException;

@RestController
@RequestMapping("/api")
public class BspController {

    Logger logger = LoggerFactory.getLogger(BspController.class);

    @Autowired
    private AsyncTaskExecutor taskExecutor;

    @PostMapping("/bsp-execution")
    public void bspExecution(@RequestPart("argsExecution") ArgsExecution argsExecution,
            @RequestPart(value = "javascriptFile", required = false) MultipartFile javascriptFile) {

        List<Future<?>> futures = new ArrayList<>();

        argsExecution.getAgendas().forEach(agenda -> {
            List<String> args = new ArrayList<String>(Arrays.asList(agenda, argsExecution.getTipoProcesso().toString(),
                    String.valueOf(argsExecution.isHeadless()), argsExecution.getPathGeckodriver()));

            if (javascriptFile != null && !javascriptFile.isEmpty()) {
                try {
                    args.add(new String(javascriptFile.getBytes(), StandardCharsets.UTF_8));
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }

            Future<?> future = taskExecutor.submit(() -> {
                try {
                    logger.info("====== NOME DA THREAD: {} =======", Thread.currentThread());

                    switch (TiposProcessoEnum.valueOf(args.get(1))) {
                        case TiposProcessoEnum.EXTRACAO_BILHETES -> ExtracaoBilhetes.realizaProcessamento(args);
                        case TiposProcessoEnum.BAIXA_ARQUIVOS -> BaixaArquivos.realizaProcessamento(args);
                        case TiposProcessoEnum.SOLICITA_REEMBOLSO -> SolicitaReembolso.realizaProcessamento(args);
                        default -> logger.error("O processo {} precisa ser um dos {}", args.get(1),
                                TiposProcessoEnum.values().toString());
                    }

                } catch (IOException | InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            });
            futures.add(future);
        });

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @PostMapping("/read-js")
    public String readJavaScriptFileFromUrl(@RequestBody MultipartFile javascriptFile)
            throws IOException, ScriptException {

        if (javascriptFile.isEmpty()) {
            return "Arquivo vazio";
        }

        String scriptContent = new String(javascriptFile.getBytes(), StandardCharsets.UTF_8);

        return scriptContent;
    }
}
