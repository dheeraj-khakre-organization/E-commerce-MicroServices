package com.ecommers.orderService.service;

import com.ecommers.orderService.dtos.*;
import com.ecommers.orderService.model.Order;
import com.ecommers.orderService.model.OrderLineItem;
import com.ecommers.orderService.model.OrderStatus;
import com.ecommers.orderService.repository.OrderLineItemRepository;
import com.ecommers.orderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private  final OrderRepository orderRepository;
   private  final OrderLineItemRepository itemRepository;
   private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest) {

       List<OrderLineItem> orderLineItems= orderRequest.getOrderLineItems().stream().map(this::mapToOrderLineItem).toList();

      Order order=  Order.builder()
                .customerName(orderRequest.getCustomerName())
                .customerEmail(orderRequest.getCustomerEmail())
                .orderLineItems(orderLineItems)
              .status(OrderStatus.NEW)
              .totalAmount(totalPrice(orderLineItems))
              .orderDate(LocalDateTime.now())
              .orderNumber(generateOrderNumber())
                .build();


// ✅ Assign back-reference so `order_id` gets populated
        orderLineItems.forEach(item -> item.setOrder(order));


      orderRepository.save(order);

       log.info("order place {}",order.getId());
    }
    private String generateOrderNumber() {
        String prefix = "ORD-";
        String timestamp = DateTimeFormatter.ofPattern("yyyyMMddHHmm").format(LocalDateTime.now());
        return prefix + timestamp;
    }
    private BigDecimal totalPrice(List<OrderLineItem> orderLineItems) {
        return orderLineItems.stream()
                .map(item -> item.getPricePerUnit().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private OrderLineItem mapToOrderLineItem(OrderLineItemRequest item) {
          // get product  from product service//
        ProductResponse productResponse = getProductResponse(item.getProductCode());
        return  OrderLineItem.builder()
                .productCode(item.getProductCode())
                .productName(productResponse.getName())
                .quantity(item.getQuantity())
                .pricePerUnit(productResponse.getPrice())
                .isAvailable(productResponse.getInventoryResponse().getIsAvailable())
                .lineTotal(productResponse.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .build();
    }
    public ProductResponse getProductResponse(String productCode) throws RuntimeException {
        try {
        return webClientBuilder.build().get().uri("http://product-service/api/product/sku-code/{productCode}",productCode)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .block(); // Use reactive chain if you're building reactively
    } catch (Exception ex) {
        throw new RuntimeException("Failed to fetch inventory: " + ex.getMessage());
    }
}

    public List<OrderResponse> getAllOrders() {
           return   orderRepository.findAll().stream().map(this::mapToOrderResponse).toList();
    }

    private OrderResponse mapToOrderResponse(Order order) {
            log.info("order  data {}",order);
            List<OrderLineItem> orderLineItems = itemRepository.findByOrder_Id(order.getId());
        log.info("orderlineitem  data {}",orderLineItems);
        return OrderResponse.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .orderDate(order.getOrderDate())
                .orderNumber(order.getOrderNumber())
                .customerEmail(order.getCustomerEmail())
                .status(String.valueOf(order.getStatus()))
                .orderLineItems(orderLineItems.stream().map(this::mapToOrderLineItemResposce).toList())
                .totalAmount(order.getTotalAmount())
                .build();
    }

    private OrderLineItemResponse mapToOrderLineItemResposce(OrderLineItem item) {
        return OrderLineItemResponse.builder()
                .lineTotal(item.getLineTotal())
                .id(item.getId())
                .pricePerUnit(item.getPricePerUnit())
                .productCode(item.getProductCode())
                .quantity(item.getQuantity())
                .productName(item.getProductName())
                .isAvailable(item.getIsAvailable())
                .build();
    }

    public OrderResponse getOrdersById(Long id) {
        try
        {
         Optional<Order> orderOptional =  orderRepository.findById(id);
            return orderOptional.map(this::mapToOrderResponse).orElse(null);

        } catch (Exception ex) {
        throw new RuntimeException("Failed to fetch order: " + ex.getMessage());
    }
    }
}
