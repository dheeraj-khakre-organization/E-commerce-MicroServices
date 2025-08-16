package com.ecommers.inventoryService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "warehouses")
public class Warehouse {

    @Id
    @SequenceGenerator(name = "warehouses_sequence", allocationSize = 1, sequenceName = "warehouses_sequence")
    @GeneratedValue(generator = "warehouses_sequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String locationName;
    private String address;
    private String zone;

    // Getters, Setters, Constructors
}