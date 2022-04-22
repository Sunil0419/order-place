package com.apriltraining.orderplace.service;

import com.apriltraining.orderplace.dao.OrderDAO;
import com.apriltraining.orderplace.entities.OrderEntity;
import com.apriltraining.orderplace.models.CreateOrderRequest;
import com.apriltraining.orderplace.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService{

    private OrderDAO orderDAO;

    public OrderServiceImpl(@Autowired OrderDAO orderDAO){
        this.orderDAO = orderDAO;
    }

    @Override
    public Order placeOrder(CreateOrderRequest createOrderRequest) {

    OrderEntity orderEntity = OrderEntity.builder()
            .orderId(UUID.randomUUID().toString())
            .customerId(createOrderRequest.getCustomerId())
            .productIds(createOrderRequest.getProductIds())
            .paidAmount(createOrderRequest.getPaidAmount())
            .status("PENDING")
            .timeCreated(new Date())
            .build();

             orderEntity = orderDAO.save(orderEntity);

        return Order.builder()
                .orderId(orderEntity.getOrderId())
                .customerId(orderEntity.getCustomerId())
                .paidAmounts(orderEntity.getPaidAmount())
                .productIds(orderEntity.getProductIds())
                .status(orderEntity.getStatus())
                .timeCreated(orderEntity.getTimeCreated())
                .build();
    }

    @Override
    public Order getOrder(String orderId) {

         OrderEntity orderEntity = orderDAO.findByOrderId(orderId);

        return Order.builder()
                .orderId(orderEntity.getOrderId())
                .customerId(orderEntity.getCustomerId())
                .paidAmounts(orderEntity.getPaidAmount())
                .productIds(orderEntity.getProductIds())
                .status(orderEntity.getStatus())
                .timeCreated(orderEntity.getTimeCreated())
                .build();
    }
}
