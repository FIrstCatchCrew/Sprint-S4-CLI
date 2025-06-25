package com.cliapp.model;

public class OrderItem {
    private Long catchId;
    private String speciesName;
    private Double quantity;
    private Double price;

    public Long getCatchId() { return catchId;}

    public String getSpeciesName() { return speciesName; }
    public void setSpeciesName(String speciesName) { this.speciesName = speciesName; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
}
