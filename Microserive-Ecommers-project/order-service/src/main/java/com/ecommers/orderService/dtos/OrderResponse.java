package com.ecommers.orderService.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private String orderNumber;

    private String customerName;
    private String customerEmail;

    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String status;

    private List<OrderLineItemResponse> orderLineItems;

}
