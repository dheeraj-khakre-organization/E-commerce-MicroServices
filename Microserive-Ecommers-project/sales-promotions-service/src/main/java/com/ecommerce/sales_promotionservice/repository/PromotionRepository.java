package com.ecommerce.sales_promotionservice.repository;

import com.ecommerce.sales_promotionservice.models.Promotion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PromotionRepository extends MongoRepository<Promotion,String> {

    List<Promotion> findByIsActiveTrue();
    List<Promotion> findByApplicableProductIdsContaining(String productId);

}
