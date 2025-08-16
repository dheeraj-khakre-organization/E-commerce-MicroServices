package com.ecommers.orderService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "order_line_items")
public class OrderLineItem {

    @Id
    @SequenceGenerator(name = "orderLineItem_sequence", allocationSize = 1, sequenceName = "orderLineItem_sequence")
    @GeneratedValue(generator = "orderLineItem_sequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String productCode;
    private String productName;

    private Integer quantity;
    private Boolean isAvailable;
    private BigDecimal pricePerUnit;

    private BigDecimal lineTotal; // quantity × pricePerUnit

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Getters, setters, constructors
}