package com.cliapp.service;

import com.cliapp.client.RESTClient;
import com.cliapp.model.CatchViewDTO;

import java.util.List;

public class CatchService {
    private final RESTClient client;

    public CatchService(RESTClient client) {
        this.client = client;
    }

    public List<CatchViewDTO> getAvailableCatches() {
        try {
            return client.getAvailableCatches();
        } catch (Exception e) {
            System.err.println("Failed to get catches: " + e.getMessage());
            return List.of();
        }
    }

    public List<CatchViewDTO> getCatchesBySpecies(String speciesName) {
        return client.getCatchesBySpecies(speciesName);
    }

//    public List<Catch> getCatchesByFisherName(String username) {
//        return client.getCatchesByFisherName(username);
//    }

    public List<CatchViewDTO> getCatchesByFisherId(long id) {
        return client.getCatchesByFisherId(id);
    }

    public List<CatchViewDTO> getSpeciesAvailableAtLanding(String landingName) {
        return client.getSpeciesAvailableAtLanding(landingName);
    }
}


