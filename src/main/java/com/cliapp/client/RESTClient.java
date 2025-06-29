package com.cliapp.client;

import com.cliapp.model.CatchViewDTO;

import com.cliapp.model.FisherProfile;
import com.cliapp.model.Order;
import com.cliapp.model.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for interacting with RESTful web services.
 * It provides methods for making HTTP requests to various API endpoints and processing their responses.
 */
public class RESTClient {

    private static final String CATCH_ENDPOINT = "/catch";
    private static final String ORDER_ENDPOINT = "/order";
    private static final String PERSON_ENDPOINT = "/person";
    private static final String FISHER_ENDPOINT = "/fisher";
    private static final String FISHER_CATCHES = "/fisher/%d/catches";

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


    private final ObjectMapper mapper;

    public RESTClient() {
        this.mapper = createDefaultMapper();
    }

    private ObjectMapper createDefaultMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    /**
     * Sends an HTTP request using the given {@code HttpRequest} and returns the response.
     * Logs the response body if the status code is 200, or logs the status code if it's an error.
     *
     * @param request the {@code HttpRequest} object containing the details of the request to send
     * @return the {@code HttpResponse<String>} object containing the response from the server
     * @throws IOException if an I/O error occurs during the operation
     * @throws InterruptedException if the operation is interrupted
     */
//    private HttpResponse<String> httpSender(HttpRequest request) throws IOException, InterruptedException {    HttpResponse<String> response = getClient().send(request, HttpResponse.BodyHandlers.ofString());
//        if (response.statusCode()!=200) {
//            System.out.println("Error Status Code: " + response.statusCode());
//        }    return response;
//    }
//

    private HttpResponse<String> httpSender(HttpRequest request) throws IOException, InterruptedException {
        // Log the request
        System.out.println("➡️  Sending Request:");
        System.out.println("URL:    " + request.uri());
        System.out.println("Method: " + request.method());

        // Optional: log headers
        request.headers().map().forEach((key, value) ->
                System.out.println("Header: " + key + " = " + String.join(", ", value))
        );

        // Send and log response
        HttpResponse<String> response = getClient().send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("⬅️  Response Status: " + response.statusCode());

        if (response.statusCode() != 200) {
            System.out.println("Error Body: " + response.body());
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
            System.err.println("Failed to fetch from " + serverURL + endpoint + ": " + e.getMessage());
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
            System.err.println("Failed to fetch from " + serverURL + endpoint + ": " + e.getMessage());
            return null;
        }
    }



    // === Public catch-related methods ===

    public CatchViewDTO getCatchById(long id) {
        return getObject(CATCH_ENDPOINT+ "/" + id, CatchViewDTO.class);
    }

    public List<CatchViewDTO> getAllCatches() {
        return getList(CATCH_ENDPOINT, new TypeReference<>() {});
    }

    public List<CatchViewDTO> getCatchesByFisherId(long id) {
        String url = String.format(FISHER_CATCHES, id);
        return getList(url, new TypeReference<>() {});
    }

    public List<CatchViewDTO> getCatchesByFisherName(String username) {
        return getList(CATCH_ENDPOINT + "/fisher/" + username, new TypeReference<>() {});
    }


    // === catches by fisher ===

    private List<CatchViewDTO> getCatchesByFisherSubpath(long id, String subPath) {
        String url = String.format(FISHER_CATCHES, id) + subPath;
        return getList(url, new TypeReference<>() {});}

    public List<CatchViewDTO> getFisherCatchesById(long id) {
        return getCatchesByFisherSubpath(id, "");
    }

    public List<CatchViewDTO> getExpiredCatchesByFisherId(long id) {
        return getCatchesByFisherSubpath(id, "/expired");
    }

    public List<CatchViewDTO> getSoldCatchesByFisherId(long id) {
        return getCatchesByFisherSubpath(id, "/sold");
    }

    // === catches by search ===
    public List<CatchViewDTO> getAvailableCatches() {
        return getList(CATCH_ENDPOINT + "/available", new TypeReference<>() {});
    }

    public List<CatchViewDTO> getSpeciesAvailableAtLanding(String landingName) {
        String encodedName = URLEncoder.encode(landingName, StandardCharsets.UTF_8);
        return getList(CATCH_ENDPOINT + "/available/" + encodedName, new TypeReference<List<CatchViewDTO>>() {});
    }

    public List<CatchViewDTO> getCatchesBySpecies(String speciesName) {
        String encodedName = URLEncoder.encode(speciesName, StandardCharsets.UTF_8);
        return getList(CATCH_ENDPOINT + "/speciesName/" + encodedName, new TypeReference<>() {});
    }

    public List<CatchViewDTO> searchCatches(String species, String landingName) {
        StringBuilder query = new StringBuilder(CATCH_ENDPOINT + "/search?");

        if (species != null) {
            String encodedSpeciesName = URLEncoder.encode(species, StandardCharsets.UTF_8);
            query.append("speciesName=").append(encodedSpeciesName).append("&");
        }
        if (landingName != null) {
            String encodedLandingName = URLEncoder.encode(landingName, StandardCharsets.UTF_8);
            query.append("pickupLocationName=").append(encodedLandingName).append("&");
        }
        if (query.charAt(query.length() - 1) == '&') {
            query.setLength(query.length() - 1);
        }

        return getList(query.toString(), new TypeReference<>() {});
    }


    // === Public fisher-related methods ===
    public FisherProfile getFisherById(long id) {
        return getObject(PERSON_ENDPOINT + "/" + id, FisherProfile.class);
    }

    public FisherProfile getFisherByUsername(String username) {
        String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8);
        return getObject(FISHER_ENDPOINT+ "/" + encodedUsername, FisherProfile.class);
    }

    public List<FisherProfile> getAllFishers() {
        return getList(FISHER_ENDPOINT, new TypeReference<>() {});
    }

    // === order-related methods ===

    public Order getOrderById(long id) {
        return getObject(ORDER_ENDPOINT + "/" + id, Order.class);
    }

    public List<Order> getOrdersByCustomer(String username) {
        String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8);
        return getList(ORDER_ENDPOINT + "/customer/" + encodedUsername, new TypeReference<>() {});
    }


    // === person-related methods ===

    public Person getUserById(long id) {
        return getObject(PERSON_ENDPOINT + "/" + id, Person.class);
    }

    public Person getUserByUsername(String username) {
        return getObject(PERSON_ENDPOINT + "/username/" + username, Person.class);
    }

    public List<Person> getAllByRoleType(String role) {
        String encodedRole = URLEncoder.encode(role, StandardCharsets.UTF_8);
        return getList(PERSON_ENDPOINT + "/roles?role=" + encodedRole, new TypeReference<>() {});
    }

    public List<Order> getAllOrders() {
        return getList(ORDER_ENDPOINT, new TypeReference<>() {});
    }
}
