package com.luanmarcene.utils;

import java.net.http.HttpResponse;

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

}
