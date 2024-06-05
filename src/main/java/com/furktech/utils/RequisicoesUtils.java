package com.furktech.utils;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.furktech.model.Solicitacao;
import com.furktech.model.SolicitacoesPorUnidadeOperacional;
import com.furktech.model.User;

public class RequisicoesUtils {

    public static User retornaUser(HttpResponse<String> dadosProcessamento)
            throws JsonMappingException, JsonProcessingException {

        String dadosBody = dadosProcessamento.body();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(dadosBody);

        User user = new User(jsonNode.get("login").asText(), jsonNode.get("senha").asText());

        return user;
    }

    public static List<SolicitacoesPorUnidadeOperacional> retornaSolicitacoes(HttpResponse<String> dadosProcessamento)
            throws JsonMappingException, JsonProcessingException {

        String dadosBody = dadosProcessamento.body();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(dadosBody);
        JsonNode solicitacoesJson = jsonNode.get("solicitacoes");

        Map<String, List<JsonNode>> solicitacoesPorUnidadeOperacional = new HashMap<>();

        for (JsonNode solicitacao : solicitacoesJson) {

            String unidadeOperacional = solicitacao.get("unidadeOperacional").asText();

            List<JsonNode> solicitacoesDaUnidade = solicitacoesPorUnidadeOperacional.getOrDefault(unidadeOperacional,
                    new ArrayList<>());
            solicitacoesDaUnidade.add(solicitacao);

            solicitacoesPorUnidadeOperacional.put(unidadeOperacional, solicitacoesDaUnidade);
        }

        /*
         * Cria os objetos Solicitacoes e os mapeia de acordo com a Lista JsonNode de
         * solicitacoesPorUnidadeOperacional
         */
        List<SolicitacoesPorUnidadeOperacional> solicitacoesPorUnidade = new ArrayList<>();

        for (Map.Entry<String, List<JsonNode>> entry : solicitacoesPorUnidadeOperacional.entrySet()) {
            SolicitacoesPorUnidadeOperacional solicitacoes = new SolicitacoesPorUnidadeOperacional();
            solicitacoes.setUnidadeOperacional(entry.getKey());

            Solicitacao solicitacao = new Solicitacao();
            entry.getValue().forEach(s -> {
                solicitacao.setUnidadeOperacional(s.get("unidadeOperacional").asText());
                solicitacao.setNumBilhete(s.get("numBilhete").asText());
                solicitacao.setSolicitacaoId(s.get("solicitacaoId").asInt());
                solicitacao.setCodigoTipoCompanhia(s.get("codigoTipoCompanhia").asText());
            });

            solicitacoes.addSolicitacao(solicitacao);

            solicitacoesPorUnidade.add(solicitacoes);
        }

        return solicitacoesPorUnidade;
    }
}
