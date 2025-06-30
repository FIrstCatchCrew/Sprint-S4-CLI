package com.cliapp.model;

import java.math.BigDecimal;

public class OrderItem {
    private Long id;
    private String speciesName;
    private BigDecimal quantity;
    private BigDecimal price;

    public OrderItem(Long id, String speciesName, BigDecimal quantity, BigDecimal price) {
        this.id = id;
        this.speciesName = speciesName;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() { return id;}

    public String getSpeciesName() { return speciesName; }
    public void setSpeciesName(String speciesName) { this.speciesName = speciesName; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
}
