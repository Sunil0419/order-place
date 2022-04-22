package com.apriltraining.orderplace.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateOrderRequest {

    private List<String> productIds;
    private String customerId;
    private float paidAmount;
}
