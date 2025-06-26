package com.cliapp.model;

public class Catch {
    private long id;
    private String species;
    private double quantityInKg;
    private double pricePerKg;
    private String pickupAddress;
    private boolean available;

    public Catch(String species, double quantityInKg, double pricePerKg, String pickupAddress, boolean available) {
        this.species = species;
        this.quantityInKg = quantityInKg;
        this.pricePerKg = pricePerKg;
        this.pickupAddress = pickupAddress;
        this.available = available;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public double getQuantityInKg() { return quantityInKg; }
    public void setQuantityInKg(double quantityInKg) { this.quantityInKg = quantityInKg; }

    public double getPricePerKg() { return pricePerKg; }
    public void setPricePerKg(double pricePerKg) { this.pricePerKg = pricePerKg; }

    public String getPickupAddress() { return pickupAddress; }
    public void setPickupAddress(String pickupAddress) { this.pickupAddress = pickupAddress; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}
