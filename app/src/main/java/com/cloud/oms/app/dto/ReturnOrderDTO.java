package com.cloud.oms.app.dto;

import lombok.Data;

@Data
public class ReturnOrderDTO {
    private String orderId;
    private int quantity;
    private String reason;
}
