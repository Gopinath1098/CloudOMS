package com.cloud.oms.app.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cloud.oms.app.Status.OrderStatus;

@Entity
@Data
public class OrderEntity {
    @Id
    private String orderId;
    @Column(name = "product_id")
    private String product;
    private int quantity;
    private double totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Email
    private String email;
    @NotBlank
    private String name;
    @Min(value = 1000000000L, message = "Invalid mobile number")
    @Max(value = 9999999999L, message = "Invalid mobile number")
    private Long mobile_no;

   @PrePersist
public void prePersist() {
    // Generate ID if not already set
    if (orderId == null) {
        this.orderId = "ORD-" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"))+java.util.UUID.randomUUID().toString().substring(0,6);
    }

    // Set default status if not provided
    if (orderStatus == null) {
        orderStatus = OrderStatus.NEW;
    }
}
    
}
