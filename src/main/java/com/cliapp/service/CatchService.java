package com.cliapp.service;

import com.cliapp.client.RESTClient;
import com.cliapp.model.CatchViewDTO;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class CatchService {
    private final RESTClient client;

    public CatchService(RESTClient client) {
        this.client = client;
    }

    public List<CatchViewDTO> getAllCatches() {
        return client.getList("/catch", new TypeReference<>() {});
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

    public List<CatchViewDTO> getCatchesByFisher(String fisher) {
        return client.getCatchesByFisher(fisher);
    }

//    public List<Catch> getCatchesByFisherName(String username) {
//        return client.getCatchesByFisherName(username);
//    }

    public List<CatchViewDTO> getFisherCatchesById(long id) {
        return client.getFisherCatchesById(id);
    }

    public List<CatchViewDTO> getSoldCatchesByFisherId(long id) {
        return client.getSoldCatchesByFisherId(id);
    }

    public List<CatchViewDTO> getSpeciesAvailableAtLanding(String landingName) {
        return client.getSpeciesAvailableAtLanding(landingName);
    }

    public List<CatchViewDTO> getCatchesByLandingName(String landingName) {
        return client.searchCatches(null, landingName, null, null);
    }
}


