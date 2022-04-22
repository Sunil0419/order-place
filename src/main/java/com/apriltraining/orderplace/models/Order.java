package com.apriltraining.orderplace.models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class Order {

    private String orderId;

    private String status;

    private Date timeCreated;

    private List<String> productIds;

    private String customerId;

    private float paidAmounts;



}
