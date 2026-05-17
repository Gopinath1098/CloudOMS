package com.cloud.oms.app.dto;

import lombok.Data;

@Data
public class ProductDTO {

    int productId;
    String productName;
    String productDesc;
    double productPrice;
    int productStock;
    String category;
    String imageUrl;
    
}
