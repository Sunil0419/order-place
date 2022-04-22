package com.apriltraining.orderplace.dao;

import com.apriltraining.orderplace.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderDAO extends JpaRepository<OrderEntity, Long> {

    OrderEntity findByOrderId(String orderId);

}
