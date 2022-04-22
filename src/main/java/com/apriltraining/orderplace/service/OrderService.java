package com.apriltraining.orderplace.service;

import com.apriltraining.orderplace.models.CreateOrderRequest;
import com.apriltraining.orderplace.models.Order;

public interface OrderService {

    Order placeOrder(CreateOrderRequest createOrderRequest);

    Order getOrder(String orderId);

}
