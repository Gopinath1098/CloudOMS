package com.cloud.oms.app.dto;

import com.cloud.oms.app.Status.OrderStatus;

import lombok.Data;

@Data
public class OrderDTO {

    String orderId;
    ProductDTO product;
    int quantity;
    double totalPrice;
    OrderStatus orderStatus;
    
}
