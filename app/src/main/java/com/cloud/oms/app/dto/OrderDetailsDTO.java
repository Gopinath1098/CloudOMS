package com.cloud.oms.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class OrderDetailsDTO {

    private String name;
    private String email;
    private Long mobile;
    
}
