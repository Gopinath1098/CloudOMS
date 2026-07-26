package com.cloud.oms.app.controller;

import java.util.List;

import com.cloud.oms.app.dto.ReturnOrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cloud.oms.app.Status.OrderStatus;
import com.cloud.oms.app.dto.OrderDTO;
import com.cloud.oms.app.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderService;

     public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to Cloud Native Order Management System for Orders")  ;
    }

    @GetMapping("auth/viewid/{id}")
    public ResponseEntity<OrderDTO> viewOrder(@PathVariable String id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("auth/vieworder/{status}")
    public ResponseEntity<List<OrderDTO>> viewOrder(@PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.getOrderByState(status));
    }

    @PostMapping("auth/placeorder")
    public ResponseEntity<String> placeOrder(@RequestBody List<OrderDTO> orderDTO) {
        return ResponseEntity.ok(orderService.placeOrder(orderDTO));
    }

    @PutMapping("auth/update/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable String id) {
        return ResponseEntity.ok(orderService.updateOrder(id));
    }

    @PutMapping("auth/return")
    public ResponseEntity<String> returnOrders(@RequestBody List<ReturnOrderDTO> orderDTO) {
        return ResponseEntity.ok(orderService.returnOrder(orderDTO));
    }

    @PatchMapping("auth/update")
    public ResponseEntity<String> updateOrderQuantity(@RequestParam String orderId,@RequestParam int quantity) {
        return ResponseEntity.ok(orderService.updateOrderQuantity(orderId,quantity)?orderId+" Sucessfully Updated":orderId+" Please Provide Valid OrderId");
    }

    @PutMapping("auth/delete/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(id+" deleted Sucessfully");
    }
}
