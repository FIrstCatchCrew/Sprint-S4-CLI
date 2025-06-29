package com.cliapp.service;

import com.cliapp.client.RESTClient;
import com.cliapp.model.CatchViewDTO;
import com.cliapp.model.FisherProfile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FisherService {
    private final RESTClient client;

    public FisherService(RESTClient client) {
        this.client = client;
    }

    public FisherProfile getFisherById(long id) {
        try {
            return client.getFisherById(id);
        } catch (Exception e) {
            System.err.println("Failed to find fisher: " + e.getMessage());
            return null;
        }
    }

    public List<CatchViewDTO> getFisherCatchesById(long id) {
        try {
            return client.getFisherCatchesById(id);
        } catch (Exception e) {
            return handleCatchError(e);
        }
    }

    public List<CatchViewDTO> getSoldCatchesByFisherId(long id) {
        try {
            return client.getSoldCatchesByFisherId(id);
        } catch (Exception e) {
            return handleCatchError(e);
        }
    }

    public List<CatchViewDTO> getExpiredCatchesByFisherId(long id) {
        try {
            return client.getExpiredCatchesByFisherId(id);
        } catch (Exception e) {
            return handleCatchError(e);
        }
    }

    public List<CatchViewDTO> getTodaysSalesByFisherId(long id) {
        try {
            return getSoldCatchesByFisherId(id).stream()
                    .filter(c -> c.getTimeStamp().toLocalDate().isEqual(LocalDate.now()))
                    .toList();
        } catch (Exception e) {
            return handleCatchError(e);
        }
    }

    public Map<String, List<CatchViewDTO>> getSoldCatchesByPort(long fisherId) {
        try {
            return getSoldCatchesByFisherId(fisherId).stream()
                    .collect(Collectors.groupingBy(c -> {
                        String port = c.getPickupLocationName();
                        return port != null && !port.isBlank() ? port : "Unknown Port";
                    }));
        } catch (Exception e) {
            System.err.println("Failed to group sold catches by port: " + e.getMessage());
            return Map.of();
        }
    }

    private List<CatchViewDTO> handleCatchError(Exception e) {
        System.err.println("Failed to get catches: " + e.getMessage());
        System.out.println("No catches available at the moment. Please try again later.");
        return List.of();
    }

}
