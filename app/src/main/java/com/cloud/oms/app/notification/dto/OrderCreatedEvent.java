package com.cloud.oms.app.notification.dto;

import lombok.Data;

@Data
public class OrderCreatedEvent {

    private String orderId;
    private String userEmail;
    private Double totalAmount;
    
}
