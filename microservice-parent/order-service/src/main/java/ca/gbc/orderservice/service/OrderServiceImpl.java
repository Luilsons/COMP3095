package ca.gbc.orderservice.service;

import ca.gbc.orderservice.client.InventoryClient; // Ensure this path is correct
import ca.gbc.orderservice.dto.OrderRequest; // Ensure this path is correct
import ca.gbc.orderservice.model.Order;
import ca.gbc.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        // Check inventory
        var isProductInStock = inventoryClient.isInStock(orderRequest.getSkuCode(), orderRequest.getQuantity());

        if (isProductInStock) {
            Order order = Order.builder()
                    .orderNumber(UUID.randomUUID().toString())
                    .price(orderRequest.getPrice())
                    .skuCode(orderRequest.getSkuCode())
                    .quantity(orderRequest.getQuantity())
                    .build();

            orderRepository.save(order); // Save order to the repository
        } else {
            throw new RuntimeException("Product with SKU code " + orderRequest.getSkuCode() + " is not in stock");
        }
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return Optional.empty();
    }
}
