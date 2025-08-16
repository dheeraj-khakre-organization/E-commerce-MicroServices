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
public class InventoryRequest {
        private Integer quantityAvailable;
        private LocalDate lastUpdated = LocalDate.now();

        // Suggested additions:
        private String productSkuCode;
        private Boolean isAvailable;
    }

