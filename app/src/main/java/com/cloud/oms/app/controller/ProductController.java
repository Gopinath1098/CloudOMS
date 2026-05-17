package com.cloud.oms.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.oms.app.dto.ProductDTO;
import com.cloud.oms.app.service.ProductService;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to Cloud Native Order Management System for Inventory");
    }

    @GetMapping("/viewproduct/{id}")
    public ResponseEntity<ProductDTO> viewProduct(@PathVariable String id) {
        ProductDTO productDTO = productService.getProductById(id);
        return ResponseEntity.ok(productDTO);
    }

    @PostMapping("/addproduct/{id}")
    public ResponseEntity<String> addProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.addProduct(productDTO).getProductId()+"added successfully");
    }

    @PutMapping("/updateproduct/{id}")
    public ResponseEntity<String> updateProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(productDTO).getProductId()+"updated successfully");
    }

    @PatchMapping("/patchInventory/{id}/{quantity}")
    public ResponseEntity<String> patchInventory(@PathVariable String id, @PathVariable int quantity) {
        // Implementation for patching inventory
        productService.updateInventory(id, quantity, true);
        return ResponseEntity.ok("Inventory updated successfully");
    }

}
