package com.cloud.oms.app.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Range;

import com.cloud.oms.app.exception.ProductNotValidException;
import com.github.f4b6a3.ulid.UlidCreator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
@Data
public class ProductEntity {

    @Id
    private String productId;
    private String productName;
    private String productDesc;
    @Range(min = 1, message = "Product price must be non-negative")
    private double productPrice;
    @Range(min = 1, message = "Product stock must be non-negative")
    private int productStock;
    private String category;
    private String imageUrl;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    public void generateId() {
        if (productId != null) {
            throw new ProductNotValidException("Product ID should not be provided. It will be generated automatically.");
        }
        this.productId = "PRD-" +LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))+java.util.UUID.randomUUID().toString().substring(0,6);
    }

}
