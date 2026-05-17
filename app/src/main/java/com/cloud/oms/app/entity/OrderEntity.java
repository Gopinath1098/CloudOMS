package com.cloud.oms.app.entity;

import com.github.f4b6a3.ulid.UlidCreator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Entity
@Data
public class OrderEntity {
    
    @Id
    String orderId;
    int userId;
    int productId;
    int quantity;
    double totalPrice;
    String orderStatus;

    @PrePersist
    public void generateId() {
        this.orderId =
            "ORD-" + UlidCreator.getUlid(System.currentTimeMillis());
    }
    
}
