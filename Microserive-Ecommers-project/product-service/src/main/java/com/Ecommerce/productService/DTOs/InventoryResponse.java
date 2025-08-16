package com.Ecommerce.productService.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponse {
    private Long id;
    private String productSkuCode;
    private Integer quantityAvailable;
    private Boolean isAvailable;
    private LocalDate lastUpdated;
}