package com.ecommers.orderService.repository;

import com.ecommers.orderService.model.OrderLineItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineItemRepository extends JpaRepository<OrderLineItem , Long> {

    List<OrderLineItem> findByOrder_Id(Long orderId);

}
