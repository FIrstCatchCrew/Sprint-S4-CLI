package com.cliapp.service;

import com.cliapp.client.RESTClient;
import com.cliapp.model.CatchViewDTO;
import com.cliapp.model.Order;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class OrderService {
    private final RESTClient client;

    public OrderService(RESTClient client) {
        this.client = client;
    }

    public List<Order> getAllOrders() {
        return client.getList("/order", new TypeReference<>() {});
    }

    public List<Order> getOrdersForCustomer(String customer) {
        return client.getOrdersByCustomer(customer);
    }

}
