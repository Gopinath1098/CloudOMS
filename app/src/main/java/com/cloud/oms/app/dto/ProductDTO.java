package com.cloud.oms.app.dto;

import lombok.Data;

@Data
public class ProductDTO {

    private String productId;
    private String productName;
    private String productDesc;
    private double productPrice;
    private int productStock;
    private String category;
    private String imageUrl;
}

