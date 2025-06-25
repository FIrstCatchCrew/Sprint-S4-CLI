package com.cliapp.client;

import com.cliapp.model.Catch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class RESTClient {
    public List<Catch> getAvailableCatches() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverURL + "/api/catch/available"))
                .build();

        try {
            HttpResponse<String> response = httpSender(request);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return mapper.readValue(response.body(), new TypeReference<>() {});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Catch> getCatchesBySpecies(String speciesName) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverURL + "/api/catch/species/" + speciesName))
                .build();

        try {
            HttpResponse<String> response = httpSender(request);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return mapper.readValue(response.body(), new TypeReference<>() {});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Order> getOrdersForCustomer(String username) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverURL + "/api/order/customer/" + username))
                .build();

        try {
            HttpResponse<String> response = httpSender(request);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return mapper.readValue(response.body(), new TypeReference<>() {});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Catch> getCatchesByFisher(String username) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverURL + "/api/catch/fisher/" + username))
                .build();

        try {
            HttpResponse<String> response = httpSender(request);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return mapper.readValue(response.body(), new TypeReference<>() {});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
