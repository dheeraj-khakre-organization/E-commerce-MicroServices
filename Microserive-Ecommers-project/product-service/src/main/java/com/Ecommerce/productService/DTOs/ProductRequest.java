package com.Ecommerce.productService.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    //private Integer inventoryContinuity; // Flag: 1 = managed, 0 = ignored
  //  private Long warehouseId;            // Needed for inventory link
    private Integer quantityAvailable;   // Initial quantity
}
