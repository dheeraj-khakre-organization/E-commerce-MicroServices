package com.ecommers.inventoryService.controller;

import com.ecommers.inventoryService.dtos.InventoryRequest;
import com.ecommers.inventoryService.dtos.InventoryResponse;
import com.ecommers.inventoryService.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService  service ;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createInventory(@RequestBody InventoryRequest inventoryRequest){
        service.createInvenory(inventoryRequest);
        return ResponseEntity.ok("inventory has created");

    }
    @GetMapping("/{productCode}")
    public ResponseEntity<?> isInventory(@PathVariable("productCode") String productCode) {
        try {
            InventoryResponse inventory = service.checkInventory(productCode);
            return ResponseEntity.ok(inventory);
        } catch (Exception ex) {
            // Custom exception for not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }
}
