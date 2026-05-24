package com.cloud.oms.app.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Range;

import com.cloud.oms.app.exception.ProductNotValidException;
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
    @Range(min = 10, message = "Product price must be non-negative")
    double productPrice;
    @Range(min = 10, message = "Product stock must be non-negative")
    int productStock;
    String category;
    String imageUrl;
    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @PrePersist
    public void generateId() {
        if(productId != null) {
            throw new ProductNotValidException("Product ID should not be provided. It will be generated automatically.");
        }
        this.productId =
            "PRD-" + UlidCreator.getHashUlid(System.currentTimeMillis(), productName).toString().substring(0, 6).toUpperCase();
    }
    
}
