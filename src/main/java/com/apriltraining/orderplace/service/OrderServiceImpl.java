package com.apriltraining.orderplace.service;

import com.apriltraining.orderplace.dao.OrderDAO;
import com.apriltraining.orderplace.entities.OrderEntity;
import com.apriltraining.orderplace.models.CreateOrderRequest;
import com.apriltraining.orderplace.models.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO;

    private RedisTemplate<String,Object> redisTemplate;

    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper;

    public OrderServiceImpl(@Autowired OrderDAO orderDAO, @Autowired KafkaTemplate<String,String> kafkaTemplate, @Autowired ObjectMapper objectMapper, @Autowired RedisTemplate<String,Object> redisTemplate) {
        this.orderDAO = orderDAO;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper=objectMapper;
        this.redisTemplate =redisTemplate;
    }


    @Override
    public Order placeOrder(CreateOrderRequest createOrderRequest) throws JsonProcessingException {

        OrderEntity orderEntity = OrderEntity.builder()
                .orderId(UUID.randomUUID().toString())
                .customerId(createOrderRequest.getCustomerId())
                .productIds(createOrderRequest.getProductIds())
                .paidAmount(createOrderRequest.getPaidAmount())
                .status("PENDING")
                .timeCreated(new Date())
                .build();

        orderEntity = orderDAO.save(orderEntity);

        Order order = Order.builder()
                .orderId(orderEntity.getOrderId())
                .customerId(orderEntity.getCustomerId())
                .paidAmounts(orderEntity.getPaidAmount())
                .productIds(orderEntity.getProductIds())
                .status(orderEntity.getStatus())
                .timeCreated(orderEntity.getTimeCreated())
                .build();

       /* key value pairs => type of the key is always a string.
        * value types
        * 1. String
        * 2. HashMap
        * 3. Set*/


        redisTemplate.opsForHash().put("ORDERS",order.getOrderId(),objectMapper.writeValueAsString(order));

        kafkaTemplate.send("topic-1", objectMapper.writeValueAsString(order));



        return order;
    }

    @Override
    public Order getOrder(String orderId) throws JsonProcessingException {

        if(redisTemplate.opsForHash().hasKey("ORDERS",orderId)){
            String orderString = (String)redisTemplate.opsForHash().get("ORDERS",orderId);

            if(orderString!=null){
                return objectMapper.readValue(orderString,Order.class);
            }
        }
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
