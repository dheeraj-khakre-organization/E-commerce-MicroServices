package com.ecommers.inventoryService.dtos;

import com.ecommers.inventoryService.model.Warehouse;
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
    private String productSkuCode;
    private Boolean isAvailable;
}

