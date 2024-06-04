package com.luanmarcene.utils;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.luanmarcene.model.User;

public class RequisicoesUtils {

    public static User retornaUser(HttpResponse<String> dadosProcessamento)
            throws JsonMappingException, JsonProcessingException {

        String dadosBody = dadosProcessamento.body();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(dadosBody);

        User user = new User(jsonNode.get("login").asText(), jsonNode.get("senha").asText());

        return user;
    }

    public static Map<String, List<JsonNode>> retornaSolicitacoes(HttpResponse<String> dadosProcessamento)
            throws JsonMappingException, JsonProcessingException {

        String dadosBody = dadosProcessamento.body();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(dadosBody);
        JsonNode solicitacoes = jsonNode.get("solicitacoes");

        Map<String, List<JsonNode>> solicitacoesPorUnidadeOperacional = new HashMap<>();

        for (JsonNode solicitacao : solicitacoes) {
            String unidadeOperacional = solicitacao.get("unidadeOperacional").asText();

            List<JsonNode> solicitacoesDaUnidade = solicitacoesPorUnidadeOperacional.getOrDefault(unidadeOperacional,
                    new ArrayList<>());
            solicitacoesDaUnidade.add(solicitacao);

            solicitacoesPorUnidadeOperacional.put(unidadeOperacional, solicitacoesDaUnidade);
        }

        return solicitacoesPorUnidadeOperacional;
    }
}
