package com.cloud.oms.app.notification.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/notifications")
@Slf4j
public class NotificationController {

    @PostMapping("/order/notify")
    public ResponseEntity<String> sendNotification(
            @RequestParam String status,@RequestParam String orderId) {
        log.info("Order with ID " + orderId + " and status " + status + " Notification sent");
        return ResponseEntity.ok(
            "Order with ID " + orderId + " and status " + status + " Notification sent"
        );
    }
}