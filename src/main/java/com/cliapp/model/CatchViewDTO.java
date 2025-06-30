package com.cliapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CatchViewDTO {
    private long id;
    private String speciesName;
    private BigDecimal quantityInKg;

    @JsonProperty("price")
    private BigDecimal pricePerKg;
    private boolean available;
    private String fisherName;
    private LocalDateTime timeStamp;
    private Long landingId;
    private double latitude;
    private double longitude;
    private String landingName;
    @JsonProperty("pickup_instructions")
    private String pickupInstructions;
    private LocalDateTime pickupTime;

    public CatchViewDTO(long id, String speciesName, BigDecimal quantityInKg, BigDecimal pricePerKg, boolean available, String fisherName, String landingName) {
        this.id = id;
        this.speciesName = speciesName;
        this.quantityInKg = quantityInKg;
        this.pricePerKg = pricePerKg;
        this.available = available;
        this.fisherName = fisherName;
        this.landingName = landingName;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public BigDecimal getQuantityInKg() {
        return quantityInKg;
    }

    public void setQuantityInKg(BigDecimal quantityInKg) {
        this.quantityInKg = quantityInKg;
    }

    public BigDecimal getPricePerKg() {
        return pricePerKg;
    }

    public void setPricePerKg(BigDecimal pricePerKg) {
        this.pricePerKg = pricePerKg;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getFisherName() {
        return fisherName;
    }

    public void setFisherName(String fisherName) {
        this.fisherName = fisherName;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getLandingId() {return landingId;}

    public void setLandingId(Long landingId) {this.landingId = landingId;}

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLandingName() {
        return landingName;
    }

    public void setLandingName(String landingName) {
        this.landingName = landingName;
    }

    public String getPickupInstructions() {
        return pickupInstructions;
    }

    public void setPickupInstructions(String pickupInstructions) {
        this.pickupInstructions = pickupInstructions;
    }

    public LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }
}