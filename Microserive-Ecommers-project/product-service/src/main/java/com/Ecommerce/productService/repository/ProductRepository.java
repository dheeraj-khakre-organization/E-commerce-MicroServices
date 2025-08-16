package com.Ecommerce.productService.repository;

import com.Ecommerce.productService.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product,String> {

    Optional<Product> findByProductSkuCode(String productSkuCode);
}
