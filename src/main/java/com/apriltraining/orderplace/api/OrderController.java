package com.apriltraining.orderplace.api;

import com.apriltraining.orderplace.models.CreateOrderRequest;
import com.apriltraining.orderplace.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value ="/api/v1/")
public class OrderController {

    OrderService orderService;

    public OrderController( @Autowired OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping(value="/order/action/place-order")
    ResponseEntity<?> placeOrder(@RequestHeader HttpHeaders httpHeaders, @RequestBody CreateOrderRequest createOrderRequest) throws JsonProcessingException {
        return ResponseEntity.ok(orderService.placeOrder(createOrderRequest));
    }

    @GetMapping(value="order/action/get-order-status")
    ResponseEntity<?> getOrderStatus(@RequestHeader HttpHeaders httpHeaders, @RequestParam String orderId){
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }
}
