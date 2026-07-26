package com.cloud.oms.app.dto;

import com.cloud.oms.app.Status.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class OrderDTO {
    @JsonIgnore
    private String orderId;
    private String productId;
    private int quantity;
    @JsonIgnore
    private double totalPrice;
    @JsonIgnore
    private OrderStatus orderStatus;
    private String name;
    private String email;
    private Long Mobile;
}
