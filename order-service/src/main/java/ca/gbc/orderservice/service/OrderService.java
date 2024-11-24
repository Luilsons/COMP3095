package ca.gbc.orderservice.service;

import ca.gbc.orderservice.dto.OrderRequest;
import ca.gbc.orderservice.model.Order;

import java.util.Optional;

public interface OrderService {

    void placeOrder(OrderRequest orderRequest);

    Optional<Order> getOrderById(Long id);  // Added method to fetch an order by ID
}
