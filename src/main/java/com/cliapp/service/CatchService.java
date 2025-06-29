package com.cliapp.service;

import com.cliapp.client.RESTClient;
import com.cliapp.model.CatchViewDTO;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CatchService {
    private final RESTClient client;

    public CatchService(RESTClient client) {
        this.client = client;
    }

    public List<CatchViewDTO> getAll() {
        try {
            return client.getList("/catch", new TypeReference<>() {
            });
        } catch (Exception e) {
            return handleCatchError(e);
        }
    }

    public List<CatchViewDTO> getAvailableCatches() {
        try {
            return client.getAvailableCatches();
        } catch (Exception e) {
            return handleCatchError(e);
        }
    }

    public List<CatchViewDTO> searchCatches(String speciesName, String landingName) {
        try {
            return client.searchCatches(speciesName, landingName);
        } catch (Exception e) {
            return handleCatchError(e);
        }
    }
    public List<CatchViewDTO> searchBySpeciesName(String species) {
        return searchCatches(species, null);

    }

    public List<CatchViewDTO> searchByLandingName(String landingName) {
        return searchCatches(null, landingName);
    }


    public List<CatchViewDTO> getCatchesByFisherName(String username) {
        long fisherId = client.getUserByUsername(username).getId();
        try {
            return client.getCatchesByFisherId(fisherId);
        } catch (Exception e) {
            return handleCatchError(e);
        }
    }

    public List<CatchViewDTO> getCatchesByFisherId(long id) {
        try {
            return client.getCatchesByFisherId(id);
        } catch (Exception e) {
            return handleCatchError(e);
        }
    }

    public List<CatchViewDTO> getSpeciesAvailableAtLanding(String landingName) {
        try {
            return client.getSpeciesAvailableAtLanding(landingName);
        } catch (Exception e) {
            return handleCatchError(e);
        }
    }

    public List<CatchViewDTO> getCatchesByLandingName(String landingName) {
        try {
            return client.searchCatches(null, landingName);
        } catch (Exception e) {
            return handleCatchError(e);
        }
    }

    public Map<String, Long> getCatchCountBySpecies(String speciesName) {
        List<CatchViewDTO> catches = searchBySpeciesName(speciesName); // this fetches from the RESTClient
        return catches.stream()
                .filter(c -> c.getSpeciesName() != null)
                .collect(Collectors.groupingBy(
                        CatchViewDTO::getSpeciesName,
                        Collectors.counting()
                ));
    }

    private List<CatchViewDTO> handleCatchError(Exception e) {
        System.err.println("Failed to get catches: " + e.getMessage());
        System.out.println("No catches available at the moment. Please try again later.");
        return List.of();
    }
}


