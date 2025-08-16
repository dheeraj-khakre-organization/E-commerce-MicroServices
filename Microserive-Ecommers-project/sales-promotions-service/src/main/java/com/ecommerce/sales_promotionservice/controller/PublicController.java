package com.ecommerce.sales_promotionservice.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/promotion")
@RequiredArgsConstructor
public class PublicController {


    @GetMapping("/health-check")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getHealth(){
        return ResponseEntity.ok("working fine");
    }

}
