package com.cloud.oms.app.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.oms.app.notification.dto.OrderCreatedEvent;

@RestController("/notifications")
public class NotificationController {

    @PostMapping("/order-created")
    public ResponseEntity<String> sendNotification(
            @RequestBody OrderCreatedEvent event,@PathVariable String status,@PathVariable String orderId) {

        return ResponseEntity.ok(
            "Order with ID " + orderId + " and status " + status + " Notification sent"
        );
    }
}