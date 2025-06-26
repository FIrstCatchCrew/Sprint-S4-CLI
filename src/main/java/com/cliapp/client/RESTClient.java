package com.cliapp.client;

import com.cliapp.model.Catch;

import com.cliapp.model.FisherProfile;
import com.cliapp.model.Order;
import com.cliapp.model.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for interacting with RESTful web services.
 * It provides methods for making HTTP requests to various API endpoints and processing their responses.
 */
public class RESTClient {

    private static final String CATCH_ENDPOINT = "/api/catch";
    private static final String ORDER_ENDPOINT = "/api/order";
    private static final String PERSON_ENDPOINT = "/api/person";
    private static final String FISHER_ENDPOINT = "/api/person/fisher";
    private static final int HTTP_OK = 200;

    private String serverURL;
    private HttpClient client;


    public String getServerURL() {
        return serverURL;
    }
    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }
    public HttpClient getClient() {
        if (client == null) {
            client  = HttpClient.newHttpClient();
        }
        return client;
    }

    private ObjectMapper createDefaultMapper() {
        return new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    private URI buildUri(String endpoint) {
        return URI.create(serverURL + endpoint);
    }

    private final ObjectMapper mapper = createDefaultMapper();

    /**
     * Sends an HTTP request using the given {@code HttpRequest} and returns the response.
     * Logs the response body if the status code is 200, or logs the status code if it's an error.
     *
     * @param request the {@code HttpRequest} object containing the details of the request to send
     * @return the {@code HttpResponse<String>} object containing the response from the server
     * @throws IOException if an I/O error occurs during the operation
     * @throws InterruptedException if the operation is interrupted
     */
    private HttpResponse<String> httpSender(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = getClient().send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode()==200) {
            System.out.println("*****Response Body Print****");
            System.out.println("***** " + response.body());
        } else {
            System.out.println("Error Status Code: " + response.statusCode());
        }
        return response;
    }

    /**
     * Sends an HTTP GET request to the specified endpoint, retrieves the response,
     * and deserializes the response body into a list of objects of the specified type.
     *
     * @param <T> the type of the objects to be returned in the list
     * @param endpoint the relative URL endpoint to append to the server base URL
     * @param typeRef a {@code TypeReference} representing the type of the list to deserialize the response into
     * @return a {@code List} of objects of the specified type if the request is successful;
     *         an empty {@code List} if an error occurs
     */
    public <T> List<T> getList(String endpoint, TypeReference<List<T>> typeRef) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverURL + endpoint))
                .build();

        try {
            HttpResponse<String> response = httpSender(request);
            return mapper.readValue(response.body(), typeRef);
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to fetch from " + endpoint + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Fetches an object of the specified type from the given endpoint of the server.
     * Makes an HTTP GET request to the provided endpoint, processes the response body,
     * and converts it into an object of the specified type.
     *
     * @param <T> the type of the object to return
     * @param endpoint the relative URL endpoint to be appended to the server base URL
     * @param clazz the class type to which the response should be deserialized
     * @return an object of the specified type if the request is successful and the response can be deserialized,
     *         or {@code null} if an error occurs during the operation
     */
    public <T> T getObject(String endpoint, Class<T> clazz) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverURL + endpoint))
                .build();

        try {
            HttpResponse<String> response = httpSender(request);
            return mapper.readValue(response.body(), clazz);
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to fetch from " + endpoint + ": " + e.getMessage());
            return null;
        }
    }



    // === Public catch-related methods ===

    public List<Catch> getAvailableCatches() {
        return getList(CATCH_ENDPOINT, new TypeReference<List<Catch>>() {});
    }

    public List<Catch> getCatchesBySpecies(String speciesName) {
        return getList(CATCH_ENDPOINT + "/species/" + speciesName, new TypeReference<List<Catch>>() {});
    }

    public List<Catch> getCatchesByFisher(String username) {
        return getList(CATCH_ENDPOINT + "/fisher/" + username, new TypeReference<List<Catch>>() {});
    }

    public List<Catch> getSpeciesAvailableAtLanding(String landingName) {
        return getList(CATCH_ENDPOINT + "/species/" + landingName, new TypeReference<List<Catch>>() {});
    }

    // === other methods ===

    public List<Order> getOrdersForCustomer(String username) {
        return getList(ORDER_ENDPOINT + "/customer/" + username, new TypeReference<List<Order>>() {});
    }

    public Person getCustomerByUsername(String username) {
        return getObject("/api/person/" + username, Person.class);
    }

    public FisherProfile getFisherByUsername(String username) {
        return getObject("/api/person/fisher/" + username, FisherProfile.class);
    }


}
