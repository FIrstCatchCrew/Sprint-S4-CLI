package com.cliapp.service;

import com.cliapp.client.RESTClient;
import com.cliapp.model.Catch;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.Collections;
import java.util.List;

public class CatchService {
    private final RESTClient client;
    private final ObjectMapper mapper;

    public CatchService(String apiBaseUrl) {
        this.client = new RESTClient();
        this.client.setServerURL(apiBaseUrl);
        this.mapper = new ObjectMapper();
    }

    public List<Catch> getAllAvailableCatches() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(client.getServerURL() + "/api/catch/available"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.getClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), new TypeReference<>() {});
            } else {
                System.out.println("Error: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("Failed to fetch available catches: " + e.getMessage());
        }

        return Collections.emptyList();
    }
}


}

