package com.Ecommerce.productService.service;

import com.Ecommerce.productService.DTOs.InventoryRequest;
import com.Ecommerce.productService.DTOs.InventoryResponse;
import com.Ecommerce.productService.DTOs.ProductRequest;
import com.Ecommerce.productService.DTOs.ProductResponse;
import com.Ecommerce.productService.model.Product;
import com.Ecommerce.productService.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.management.InstanceNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private  final ProductRepository productRepository;
    private final WebClient.Builder webClientBuilder;

    public void creteProduct(ProductRequest  productRequest){

        if (productRequest.getName() == null || productRequest.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product name is required");
        }
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .productSkuCode(generateProductCodeSKU(productRequest.getName()))
                .build();




        //  call inventory to save inventory product details
     String inventory=   createInventory(InventoryRequest.builder()
                .isAvailable(true)
                .quantityAvailable(productRequest.getQuantityAvailable())
                .productSkuCode(product.getProductSkuCode())
                .lastUpdated(LocalDate.now())
                .build());
        log.info(inventory);
        productRepository.save(product);
        log.info("product {} is save ",product.getId());
    }

    public List<ProductResponse> getAllProduct() {
        List<Product> products = productRepository.findAll();

        return    products.stream().map(this::matToProductResponse).toList();

    }
    public String createInventory(InventoryRequest request) {
        return webClientBuilder.build()
                .post()
                .uri("http://inventory-service/api/inventory")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Use async alternatives if needed
    }
    public String generateProductCodeSKU(String productName) {
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Product name must not be null or empty");
        }
        String dateCode = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String nameHash = Integer.toHexString(productName.hashCode()).toUpperCase().substring(0, 4);
        return "PROD-" + dateCode + "-" + nameHash;
    }
    private ProductResponse matToProductResponse(Product product) {
        InventoryResponse inventoryResponse = checkInventory(product.getProductSkuCode());
        return  ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .inventoryResponse(inventoryResponse)
                .price(product.getPrice())
                .build();
    }

    public InventoryResponse checkInventory(String productCode) {
        try {
            return webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory/{productCode}", productCode)
                    .retrieve()
                    .bodyToMono(InventoryResponse.class)
                    .block(); // Use reactive chain if you're building reactively
        } catch (Exception ex) {
            throw new RuntimeException("Failed to fetch inventory: " + ex.getMessage());
        }
    }


    public ProductResponse getById(String id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            log.info("product found by id {}",product.get().getId());

            return matToProductResponse(product.get());

        } else {
            // Option 1: Throw a custom exception
            throw new IllegalArgumentException(" product not found");

        }
    }

    public ProductResponse getbySkuCode(String skuCode) {
        Optional<Product> product = productRepository.findByProductSkuCode(skuCode);

        if (product.isPresent()) {
            log.info("product found by id {}",product.get().getId());

            return matToProductResponse(product.get());

        } else {
            // Option 1: Throw a custom exception
            throw new IllegalArgumentException(" product not found");
        }
    }
}
