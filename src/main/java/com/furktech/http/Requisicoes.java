package com.furktech.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.cdimascio.dotenv.Dotenv;

public class Requisicoes {

        static final Logger logger = LoggerFactory.getLogger(Requisicoes.class);
        static final Dotenv dotenv = Dotenv.load();

        static final String username = dotenv.get("API_USERNAME");
        static final String password = dotenv.get("API_PASSWORD");
        static final String authString = username + ":" + password;

        public static HttpResponse<String> getDadosProcessamento(String agenda)
                        throws IOException, InterruptedException {

                String authHeader = "Basic " + Base64.getEncoder().encodeToString(authString.getBytes());

                // Busca os dados de processamento para a agenda recebida como argumento.
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(dotenv.get("API_DADOS_PROCESSAMENTO_URL")
                                                + agenda))
                                .header("Authorization", authHeader)
                                .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // Loga o response status e o corpo
                logger.info("Response status code: {}", response.statusCode());
                logger.info("Response body: {}", response.body());

                return response;
        }

        public static void postEnviaDadosBilhete() {
        }

}