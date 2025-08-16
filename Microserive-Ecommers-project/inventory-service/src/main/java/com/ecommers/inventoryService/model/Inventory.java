package com.ecommers.inventoryService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @SequenceGenerator(name = "inventory_sequence", allocationSize = 1, sequenceName = "inventory_sequence")
    @GeneratedValue(generator = "inventory_sequence", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String productCode;
//    @ManyToOne
//    @JoinColumn(name = "product_id", nullable = false)
//    private Product product;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    private Integer quantityAvailable;
    private Boolean isAvailable;
    private LocalDate lastUpdated;

    // Getters, Setters, Constructors
}