package com.ecommers.orderService.repository;

import com.ecommers.orderService.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order , Long> {
}
