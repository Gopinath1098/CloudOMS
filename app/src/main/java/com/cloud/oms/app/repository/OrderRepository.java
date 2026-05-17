package com.cloud.oms.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cloud.oms.app.Status.OrderStatus;
import com.cloud.oms.app.entity.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    @Query("SELECT o FROM OrderEntity o WHERE o.orderState = :state")
    List<OrderEntity> findByOrderState(@Param("state") OrderStatus state);
    
}
