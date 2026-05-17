package com.cloud.oms.app.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.github.f4b6a3.ulid.UlidCreator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
@Entity
public class ProductEntity {

    @Id
    String productId;
    String productName;
    String productDesc;
    double productPrice;
    int productStock;
    String category;
    String imageUrl;
    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @PrePersist
    public void generateId() {
        this.productId =
            "PRD-" + UlidCreator.getHashUlid(System.currentTimeMillis(), productName);
    }
    
}
