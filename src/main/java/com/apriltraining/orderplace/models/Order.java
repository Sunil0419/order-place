package com.apriltraining.orderplace.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private String orderId;

    private String status;

    private Date timeCreated;

    private List<String> productIds;

    private String customerId;

    private float paidAmounts;



}
