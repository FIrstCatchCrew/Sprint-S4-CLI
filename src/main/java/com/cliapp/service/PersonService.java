package com.cliapp.service;

import com.cliapp.client.RESTClient;
import com.cliapp.model.FisherProfile;
import com.cliapp.model.Person;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class PersonService {

    private final RESTClient client;

    public PersonService(RESTClient client) {
        this.client = client;
    }

    public Person authenticate(String email, String password) {
        String endpoint = "/person/login";

        try {
            // Create JSON body
            String json = new ObjectMapper().writeValueAsString(Map.of(
                    "email", email,
                    "password", password
            ));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(client.getServerURL() + endpoint))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.getClient().send(request, HttpResponse.BodyHandlers.ofString());

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

    public List<FisherProfile> getAllFishers() {
        try {
            return client.getAllFishers();
        } catch (Exception e) {
            System.err.println("Failed to get fishers: " + e.getMessage());
            return List.of();
        }
    }

    public List<Person> getAllByRoleType(String role) {
        try {
            return client.getAllByRoleType(role);
        } catch (Exception e) {
            System.err.println("Failed to get fishers: " + e.getMessage());
            return List.of();
        }
    }
}
