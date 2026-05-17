package com.cloud.oms.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping("/")
    public String home() {
        return "Welcome to Cloud Native Order Management System for Orders";
    }

    @GetMapping("/vieworder")
    public ResponseEntity<String> viewOrder() {
        return ResponseEntity.ok("View Order");
    }

    @PostMapping("/placeorder")
    public ResponseEntity<String> placeOrder() {
        return ResponseEntity.ok("Place Order");
    }

}
