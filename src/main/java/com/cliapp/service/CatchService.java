package com.cliapp.service;

import com.cliapp.client.RESTClient;
import com.cliapp.model.CatchViewDTO;
import com.fasterxml.jackson.core.type.TypeReference;

import java.math.BigDecimal;
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

    public List<CatchViewDTO> searchCatches(String species, String pickupAddress, BigDecimal minPrice, BigDecimal maxPrice) {
        try {
            return client.searchCatches(species, pickupAddress, minPrice, maxPrice);
        } catch (Exception e) {
            return handleCatchError(e);
        }
    }
    public List<CatchViewDTO> searchBySpecies(String species) {
        return searchCatches(species, null, null, null);
    }

    public List<CatchViewDTO> searchByLanding(String portName) {
        return searchCatches(null, portName, null, null);
    }

    public List<CatchViewDTO> searchByPriceRange(BigDecimal min, BigDecimal max) {
        return searchCatches(null, null, min, max);
    }


    public List<CatchViewDTO> getCatchesBySpecies(String speciesName) {
        try {
            return client.getCatchesBySpecies(speciesName);
        } catch (Exception e) {
            return handleCatchError(e);
        }
    }

    public List<CatchViewDTO> getCatchesByFisherName(String name) {
        try {
            return client.getCatchesByFisherName(name);
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
            return client.searchCatches(null, landingName, null, null);
        } catch (Exception e) {
            return handleCatchError(e);
        }
    }

    public Map<String, Long> getCatchCountByLanding(String landing) {
        List<CatchViewDTO> catches = getCatchesByLandingName(landing); // this fetches from the RESTClient
        return catches.stream()
                .filter(c -> c.getPickupLocationName() != null)
                .collect(Collectors.groupingBy(
                        CatchViewDTO::getPickupLocationName,
                        Collectors.counting()
                ));
    }

    private List<CatchViewDTO> handleCatchError(Exception e) {
        System.err.println("Failed to get catches: " + e.getMessage());
        System.out.println("No catches available at the moment. Please try again later.");
        return List.of();
    }
}


