package com.cloud.oms.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.oms.app.Status.OrderStatus;
import com.cloud.oms.app.dto.OrderDTO;
import com.cloud.oms.app.service.OrderService;

@RestController
public class OrderController {

    private OrderService orderService;

     public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to Cloud Native Order Management System for Orders")  ;
    }

    @GetMapping("/vieworder/{id}")
    public ResponseEntity<OrderDTO> viewOrder(@PathVariable String id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/vieworder/{status}")
    public ResponseEntity<List<OrderDTO>> viewOrder(@PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.getOrderByState(status));
    }

    @PostMapping("/placeorder/")
    public ResponseEntity<String> placeOrder(@RequestBody List<OrderDTO> orderDTO) {
        return ResponseEntity.ok(orderService.placeOrder(orderDTO));
    }

    @PutMapping("/updateorder/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable String id) {
        return ResponseEntity.ok(orderService.updateOrder(id));
    }

    @PutMapping("/returnorder/")
    public ResponseEntity<String> returnOrders(@RequestBody List<OrderDTO> orderDTO) {
        return ResponseEntity.ok(orderService.returnOrder(orderDTO));
    }

}
