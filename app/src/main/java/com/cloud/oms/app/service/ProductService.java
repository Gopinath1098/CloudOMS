package com.cloud.oms.app.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cloud.oms.app.dto.ProductDTO;
import com.cloud.oms.app.entity.ProductEntity;
import com.cloud.oms.app.repository.ProductRepository;

public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO getProductById(String productId) {
        // Implement logic to retrieve product by ID
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        return ConvertToDTO(productEntity);
    }

    public ProductDTO addProduct(ProductDTO productDTO) {
        // Implement logic to add a new product
        ProductEntity productEntity = ConvertToEntity(productDTO);
        ProductEntity savedProduct = productRepository.save(productEntity);
        return ConvertToDTO(savedProduct);
    }

    public List<ProductDTO> getAllProducts() {
        // Implement logic to retrieve all products
        List<ProductEntity> productEntities = productRepository.findAll();
        return productEntities.stream().map(ProductService::ConvertToDTO).toList();
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {
        // Implement logic to update an existing product
        ProductEntity existingProduct = productRepository.findById(productDTO.getProductId()).orElseThrow(() -> new RuntimeException("Product not found with id: " + productDTO.getProductId()));
        existingProduct.setProductName(productDTO.getProductName());
        existingProduct.setProductDesc(productDTO.getProductDesc());
        existingProduct.setProductPrice(productDTO.getProductPrice());
        existingProduct.setProductStock(productDTO.getProductStock());
        existingProduct.setCategory(productDTO.getCategory());
        existingProduct.setImageUrl(productDTO.getImageUrl());
        ProductEntity updatedProduct = productRepository.save(existingProduct);
        return ConvertToDTO(updatedProduct);
    }
    @Transactional(rollbackFor = Exception.class)
    public void updateInventory(String productId, int quantity, boolean isReturn) {
        // Implement logic to update product inventory
        ProductEntity existingProduct = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        if(isReturn) {
            existingProduct.setProductStock(existingProduct.getProductStock() + quantity);
        } else {
            if(existingProduct.getProductStock() < quantity || existingProduct.getProductStock() <= 0) {
                throw new RuntimeException("Inventory is out of stock for product with id: " + productId);
            }
            existingProduct.setProductStock(existingProduct.getProductStock() - quantity);
        }
        productRepository.save(existingProduct);
    }
    
     public static ProductDTO ConvertToDTO(ProductEntity productEntity) {
        // Implement logic to convert OrderEntity to OrderDTO
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(productEntity.getProductId());
        productDTO.setProductName(productEntity.getProductName());
        productDTO.setProductDesc(productEntity.getProductDesc());
        productDTO.setProductPrice(productEntity.getProductPrice());
        productDTO.setProductStock(productEntity.getProductStock());
        productDTO.setCategory(productEntity.getCategory());
        productDTO.setImageUrl(productEntity.getImageUrl());
        return productDTO;
    }

    static ProductEntity ConvertToEntity(ProductDTO product) {
        // Implement logic to convert OrderDTO to OrderEntity
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductId(product.getProductId());
        productEntity.setProductName(product.getProductName());
        productEntity.setProductDesc(product.getProductDesc());
        productEntity.setProductPrice(product.getProductPrice());
        productEntity.setProductStock(product.getProductStock());
        productEntity.setCategory(product.getCategory());
        productEntity.setImageUrl(product.getImageUrl());
        return productEntity;
    }

}
