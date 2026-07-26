package com.cloud.oms.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cloud.oms.app.dto.ProductDTO;
import com.cloud.oms.app.service.ProductService;

@RestController
@RequestMapping("/api/product")
class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<String> home() {
        return ResponseEntity.ok(" Welcome to Cloud Native Order Management System for Inventory");
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<ProductDTO> viewProduct(@PathVariable String id) {
        ProductDTO productDTO = productService.getProductById(id);
        return ResponseEntity.ok(productDTO);
    }

    @PostMapping("auth/add/{id}")
    public ResponseEntity<String> addProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.addProduct(productDTO).getProductId()+" added successfully");
    }

    @PutMapping("auth/update/{id}")
    public ResponseEntity<String> updateProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(productDTO).getProductId()+" updated successfully");
    }

    @PatchMapping("auth/patch")
    public ResponseEntity<String> patchInventory(@RequestParam String id, @RequestParam int quantity) {
        // Implementation for patching inventory
        productService.updateInventory(id, quantity, true);
        return ResponseEntity.ok(" Inventory updated successfully");
    }
    @DeleteMapping("auth/delete")
    public ResponseEntity<String> deleteProduct(@PathVariable String id){
        productService.deleteProduct(id);
        return ResponseEntity.ok(id+" Deleted Successfully");
    }
}
