package com.Ecommerce.productService.controller;

import com.Ecommerce.productService.DTOs.ProductRequest;
import com.Ecommerce.productService.DTOs.ProductResponse;
import com.Ecommerce.productService.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest){

         try {
             productService.creteProduct(productRequest);

             return ResponseEntity.ok("product created");
         }catch (Exception ex){
             return ResponseEntity.ok(ex);
         }

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProdcuct(){
        return  productService.getAllProduct();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductResponse> getbyId(@PathVariable String id) {
        ProductResponse productResponse = productService.getById(id);

        if (productResponse != null) {
            return ResponseEntity.ok(productResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/sku-code/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductResponse> getbySkuCode(@PathVariable("skuCode") String skuCode) {
        ProductResponse productResponse = productService.getbySkuCode(skuCode);

        if (productResponse != null) {
            return ResponseEntity.ok(productResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
