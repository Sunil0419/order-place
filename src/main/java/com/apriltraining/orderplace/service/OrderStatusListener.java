package com.apriltraining.orderplace.service;


import com.apriltraining.orderplace.dao.OrderDAO;
import com.apriltraining.orderplace.entities.OrderEntity;
import com.apriltraining.orderplace.models.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusListener {

    private OrderDAO orderDAO;

    private ObjectMapper objectMapper;

    public OrderStatusListener(@Autowired OrderDAO orderDAO,@Autowired ObjectMapper objectMapper){
        this.orderDAO=orderDAO;
        this.objectMapper= objectMapper;
    }

    @KafkaListener(topics = "topic-2",groupId = "custom_group")
    public void listenGroupFoo(String message) throws JsonProcessingException {
        System.out.println("Received message in order place service: " + message);

        Order order= objectMapper.readValue(message,Order.class);            // String to order class

        OrderEntity orderEntity = orderDAO.findByOrderId(order.getOrderId());
        orderEntity.setStatus(order.getStatus());
        orderDAO.save(orderEntity);
    }
}
