package com.ecommers.inventoryService.service;


import com.ecommers.inventoryService.dtos.InventoryRequest;
import com.ecommers.inventoryService.dtos.InventoryResponse;
import com.ecommers.inventoryService.model.Inventory;
import com.ecommers.inventoryService.model.Warehouse;
import com.ecommers.inventoryService.repository.InventoryRepository;
import com.ecommers.inventoryService.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private  final InventoryRepository inventoryRepository;
    private final WarehouseRepository warehouseRepository;

    @Transactional
    public void  createInvenory(InventoryRequest inventoryRequest){
        Warehouse warehouse = Warehouse.builder()
                .address("04 ayodhya extension")
                .zone("india")
                .locationName("bhopal")
              .build();
        warehouseRepository.save(warehouse);
        Inventory inventory = Inventory.builder()
                .productCode(inventoryRequest.getProductSkuCode())
                .quantityAvailable(inventoryRequest.getQuantityAvailable())
                .lastUpdated(inventoryRequest.getLastUpdated())
                .warehouse(warehouse)
                .isAvailable(inventoryRequest.getIsAvailable())
                .build();


        inventoryRepository.save(inventory);
    }

    public InventoryResponse checkInventory(String productCode) {
        Inventory inventory = inventoryRepository.findByProductCode(productCode)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for product SKU code: " + productCode));

        return InventoryResponse.builder()
                .id(inventory.getId()) // Assuming your builder uses lowercase 'id'
                .productSkuCode(inventory.getProductCode())
                .isAvailable(inventory.getIsAvailable())
                .lastUpdated(inventory.getLastUpdated())
                .quantityAvailable(inventory.getQuantityAvailable())
                .build();
    }
}
