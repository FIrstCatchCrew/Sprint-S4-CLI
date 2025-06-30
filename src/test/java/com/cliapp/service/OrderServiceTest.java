package com.cliapp.service;

import static org.junit.jupiter.api.Assertions.*;

import com.cliapp.client.RESTClient;
import com.cliapp.model.Order;
import com.cliapp.model.OrderItem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private RESTClient mockClient;
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        mockClient = mock(RESTClient.class);
        orderService = new OrderService(mockClient);
    }

    @Test
    public void testGetAllOrdersSuccess() {
        List<OrderItem> items = List.of(
                new OrderItem(1L, "Cod", new BigDecimal("3.5"), new BigDecimal("12.99"))
        );
        List<Order> orders = List.of(
                new Order(items, "COMPLETED", LocalDateTime.now(), "customer1", 100L)
        );
        when(mockClient.getAllOrders()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertEquals(1, result.size());
        assertEquals("customer1", result.get(0).getCustomerUsername());
    }

    @Test
    public void testGetAllOrdersFailure() {
        when(mockClient.getAllOrders()).thenThrow(new RuntimeException("Failed to fetch orders"));

        List<Order> result = orderService.getAllOrders();

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetOrdersForCustomerSuccess() {
        List<OrderItem> items = List.of(
                new OrderItem(2L, "Halibut", new BigDecimal("2.0"), new BigDecimal("24.50"))
        );
        List<Order> orders = List.of(
                new Order(items, "PENDING", LocalDateTime.now(), "customerX", 101L)
        );
        when(mockClient.getOrdersByCustomer("customerX")).thenReturn(orders);

        List<Order> result = orderService.getOrdersForCustomer("customerX");

        assertEquals(1, result.size());
        assertEquals("Halibut", result.get(0).getOrderItems().get(0).getSpeciesName());
    }

    @Test
    public void testGetOrdersForCustomerFailure() {
        when(mockClient.getOrdersByCustomer("ghostUser")).thenThrow(new RuntimeException("User not found"));

        List<Order> result = orderService.getOrdersForCustomer("ghostUser");

        assertTrue(result.isEmpty());
    }
}