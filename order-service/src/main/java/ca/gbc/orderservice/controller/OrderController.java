package ca.gbc.orderservice.controller;

import ca.gbc.orderservice.dto.OrderRequest;
import ca.gbc.orderservice.model.Order;
import ca.gbc.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        try {
            orderService.placeOrder(orderRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Order Placed Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to place order: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(order -> ResponseEntity.ok().body(order))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
