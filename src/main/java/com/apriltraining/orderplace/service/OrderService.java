package com.apriltraining.orderplace.service;

import com.apriltraining.orderplace.models.CreateOrderRequest;
import com.apriltraining.orderplace.models.Order;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface OrderService {

    Order placeOrder(CreateOrderRequest createOrderRequest) throws JsonProcessingException;

    Order getOrder(String orderId);

}
