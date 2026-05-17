package com.cloud.oms.app.dto;

import lombok.Data;

@Data
public class OrderDTO {

    int orderId;
    int userId;
    int productId;
    int quantity;
    double totalPrice;
    String orderStatus;
    
}
