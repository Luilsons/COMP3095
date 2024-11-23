package ca.gbc.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderRequest {
        private Long id;
        private String orderNumber;
        private String skuCode;
        private BigDecimal price;
        private Integer quantity;
}
