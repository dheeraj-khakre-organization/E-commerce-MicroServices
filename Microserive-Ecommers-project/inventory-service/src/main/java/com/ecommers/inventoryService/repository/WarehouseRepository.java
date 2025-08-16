package com.ecommers.inventoryService.repository;

import com.ecommers.inventoryService.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
