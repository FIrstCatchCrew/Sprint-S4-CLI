package com.cliapp.model;

import java.time.LocalDateTime;
import java.util.List;


public class Order {

    private long id;
    private String customerUsername;
    private LocalDateTime orderDateTime;
    private String orderStatus;
    private List<OrderItem> orderItems;

    public Order() {
    }

    public Order(List<OrderItem> orderItems, String orderStatus, LocalDateTime orderDateTime, String customerUsername, long id) {
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
        this.orderDateTime = orderDateTime;
        this.customerUsername = customerUsername;
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }
    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }
    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
