package com.cloud.oms.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/")
    public String home() {
        return "Welcome to Cloud Native Order Management System for Inventory";
    }

    @GetMapping("/viewproduct")
    public String viewProduct() {
        return "View Product";
    }

    @PostMapping("/addproduct")
    public String addProduct() {
        return "Add Product";
    }

    @PutMapping("/updateproduct")
    public String updateProduct() {
        return "Update Product";
    }

    @PatchMapping("/patchproduct")
    public String patchProduct() {
        return "Patch Product";
    }

}
