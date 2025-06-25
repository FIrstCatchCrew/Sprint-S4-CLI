package com.cliapp.service;

import com.cliapp.client.RESTClient;
import com.cliapp.model.Catch;

import java.util.List;

public class CatchService {
    private final RESTClient client;

    public CatchService(RESTClient client) {
        this.client = client;
    }

    public List<Catch> getAvailableCatches() {
        return client.getAvailableCatches();
    }

    public List<Catch> getCatchesBySpecies(String speciesName) {
        return client.getCatchesBySpecies(speciesName);
    }

    public List<Catch> getCatchesByFisher(String username) {
        return client.getCatchesByFisher(username);
    }

    public List<Catch> getSpeciesAvailableAtLanding(String landingName) {
        return client.getSpeciesAvailableAtLanding(landingName);
    }
}


