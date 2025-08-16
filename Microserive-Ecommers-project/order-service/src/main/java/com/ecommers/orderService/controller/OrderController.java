package com.ecommers.orderService.controller;

import com.ecommers.orderService.dtos.OrderRequest;
import com.ecommers.orderService.dtos.OrderResponse;
import com.ecommers.orderService.service.OrderService;
import jakarta.ws.rs.PATCH;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest){

        orderService.placeOrder(orderRequest);
        return ResponseEntity.ok("order successfully Placed");

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderResponse>> getAllOrder(){

      List<OrderResponse>  orderResponseList = orderService.getAllOrders();
        return ResponseEntity.ok(orderResponseList);

    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrderResponse> getOrderBiId(@PathVariable("id") Long id){

        OrderResponse  orderResponseList = orderService.getOrdersById(id);
        return ResponseEntity.ok(orderResponseList);

    }


}
