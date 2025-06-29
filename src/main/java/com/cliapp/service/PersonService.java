package com.cliapp.service;

import com.cliapp.client.RESTClient;
import com.cliapp.model.FisherProfile;
import com.cliapp.model.Person;

import java.util.List;

public class PersonService {

    private final RESTClient client;

    public PersonService(RESTClient client) {
        this.client = client;
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

    public Person getUserById(long id) {
        try {
            return client.getUserById(id);
        } catch (Exception e) {
            System.err.println("Failed to find user: " + e.getMessage());
            return null;
        }
    }

    public Person getUserByUsername(String username) {
        try {
            return client.getUserByUsername(username);
        } catch (Exception e) {
            System.err.println("Failed to find user: " + e.getMessage());
            return null;
        }
    }

}
