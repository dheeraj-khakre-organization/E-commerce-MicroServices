package com.ecommerce.emailservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag {
    private String type;   // e.g., "ORDER_ID", "ITEM_ID", "TRACKING_ID"
    private String value;  // e.g., "ORD123", "SKU458", "TRK789"

}