package com.ecommerce.sales_promotionservice.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "promotions")
public class Promotion {
    @Id
    private String id;

    private String title;
    private String description;
    private DiscountType discountType; // ENUM: PERCENTAGE, FIXED, BOGO
    private Double discountValue;

    private List<String> applicableProductIds;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Boolean isActive;
    private List<String> targetUserSegments;
    private List<String> bannerUrls;
}