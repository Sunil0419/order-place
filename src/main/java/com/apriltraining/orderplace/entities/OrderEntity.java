package com.apriltraining.orderplace.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name ="orders.order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name ="order_id")
    private String orderId;

    @Column(name="status")
    private String status;

    @Column(name="time_created")
    private Date timeCreated;


    @ElementCollection
    @Column(name="product_id")
    private List<String> productIds;

    @Column(name="customer_id")
    private String customerId;

    @Column(name="paid_amount")
    private float paidAmount;



}
