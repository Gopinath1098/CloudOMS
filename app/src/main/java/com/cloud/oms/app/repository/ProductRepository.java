package com.cloud.oms.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloud.oms.app.entity.ProductEntity;

@Repository
public interface  ProductRepository extends JpaRepository<ProductEntity, String> {
    
}
