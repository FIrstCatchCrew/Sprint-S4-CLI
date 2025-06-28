package com.cliapp.service;

import com.cliapp.client.RESTClient;
import com.cliapp.model.Person;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class PersonService {

    private final RESTClient restClient;
    public PersonService() {
        this.restClient = new RESTClient();
        this.restClient.setServerURL("http://localhost:8080"); // ⬅️ Or wherever your backend is running
    }

    public Person authenticate(String email, String password) {
        String endpoint = "/api/person/login";

        try {
            // Create JSON body
            String json = new ObjectMapper().writeValueAsString(Map.of(
                    "email", email,
                    "password", password
            ));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(restClient.getServerURL() + endpoint))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = restClient.getClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return new ObjectMapper()
                        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                        .readValue(response.body(), Person.class);
            }

        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
        }

        return null;
    }

}
