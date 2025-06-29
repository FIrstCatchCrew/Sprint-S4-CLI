package com.cliapp.service;

import com.cliapp.client.RESTClient;
import com.cliapp.model.Order;

import java.util.List;

public class OrderService {
    private final RESTClient client;

    public OrderService(RESTClient client) {
        this.client = client;
    }

    public List<Order> getAllOrders() {
        try {
            return client.getAllOrders();
        } catch (Exception e) {
            return handleCatchError(e);
        }
    }

    public List<Order> getOrdersForCustomer(String customer) {
        try {
            return client.getOrdersByCustomer(customer);
        } catch (Exception e) {
            return handleCatchError(e);
        }
    }

    private List<Order> handleCatchError(Exception e) {
        System.err.println("Failed to get orders: " + e.getMessage());
        System.out.println("No orders available at the moment. Please try again later.");
        return List.of();
    }

}
