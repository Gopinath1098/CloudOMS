package com.cloud.oms.app.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cloud.oms.app.Status.OrderStatus;
import com.github.f4b6a3.ulid.UlidCreator;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Entity
@Data
public class OrderEntity {
    
    @Id
    String orderId;
    @OneToOne
    @JoinColumn(name = "product_id")
    ProductEntity product;
    int quantity;
    double totalPrice;
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;
    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;

   @PrePersist
public void prePersist() {
    // Generate ID if not already set
    if (orderId == null) {
        this.orderId = "ORD-" + UlidCreator.getUlid(System.currentTimeMillis());
    }

    // Set default status if not provided
    if (orderStatus == null) {
        orderStatus = OrderStatus.NEW;
    }
}

    
}
