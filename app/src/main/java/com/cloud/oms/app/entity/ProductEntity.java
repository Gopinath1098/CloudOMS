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

@Entity
public class ProductEntity {

    @Id
    private String productId;
    private String productName;
    private String productDesc;
    @Range(min = 10, message = "Product price must be non-negative")
    private double productPrice;
    @Range(min = 10, message = "Product stock must be non-negative")
    private int productStock;
    private String category;
    private String imageUrl;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public ProductEntity() {}

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getProductDesc() { return productDesc; }
    public void setProductDesc(String productDesc) { this.productDesc = productDesc; }
    public double getProductPrice() { return productPrice; }
    public void setProductPrice(double productPrice) { this.productPrice = productPrice; }
    public int getProductStock() { return productStock; }
    public void setProductStock(int productStock) { this.productStock = productStock; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @PrePersist
    public void generateId() {
        if (productId != null) {
            throw new ProductNotValidException("Product ID should not be provided. It will be generated automatically.");
        }
        this.productId = "PRD-" + UlidCreator.getHashUlid(System.currentTimeMillis(), productName)
                .toString().substring(0, 6).toUpperCase();
    }

}
