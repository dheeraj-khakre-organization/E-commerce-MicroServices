package com.ecommers.orderService.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLineItemResponse {
    private String productCode;
    private String productName;
    private Long id;
    private Boolean isAvailable;
    private Integer quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal lineTotal;
}
