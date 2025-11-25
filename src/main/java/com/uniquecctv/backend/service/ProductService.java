package com.uniquecctv.backend.service;

import com.uniquecctv.backend.dto.ProductDto;
import com.uniquecctv.backend.entity.Product;
import com.uniquecctv.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> getProductsByCategory(String category) {
        return productRepository.findByCategory(category).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToDto(product);
    }

    public ProductDto createProduct(ProductDto productDto) {
        // Validate required fields
        if (productDto.getName() == null || productDto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (productDto.getPrice() == null || productDto.getPrice() < 0) {
            throw new IllegalArgumentException("Valid price is required");
        }
        
        Product product = convertToEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // ✅ CORRECT: Use getImage() for filename
        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setCategory(productDto.getCategory());
        existingProduct.setImage(productDto.getImage()); // ✅ CORRECT: filename only
        existingProduct.setSpecifications(productDto.getSpecifications());
        existingProduct.setInStock(productDto.getInStock());
        existingProduct.setStockQuantity(productDto.getStockQuantity());

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDto(updatedProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private ProductDto convertToDto(Product product) {
        // ✅ CORRECT: Use constructor for DTO conversion
        return new ProductDto(product);
    }

    private Product convertToEntity(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.getName() != null ? dto.getName().trim() : null);
        product.setDescription(dto.getDescription() != null ? dto.getDescription().trim() : null);
        product.setPrice(dto.getPrice() != null ? dto.getPrice() : 0.0);
        product.setCategory(dto.getCategory() != null ? dto.getCategory().trim() : null);
        
        // ✅ CORRECT: Use cleanImagePath helper method consistently
        product.setImage(cleanImagePath(dto.getImage()));
        
        product.setSpecifications(dto.getSpecifications());
        product.setInStock(dto.getInStock() != null ? dto.getInStock() : false);
        product.setStockQuantity(dto.getStockQuantity() != null ? dto.getStockQuantity() : 0);
        return product;
    }

    // ✅ CORRECT: Single helper method for image path cleaning
    private String cleanImagePath(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            return null;
        }
        
        imagePath = imagePath.trim();
        
        // Remove API path prefix if present
        if (imagePath.contains("/api/images/")) {
            imagePath = imagePath.replace("/api/images/", "");
        }
        
        // Extract just the filename from any path
        if (imagePath.contains("/")) {
            String[] parts = imagePath.split("/");
            imagePath = parts[parts.length - 1];
        }
        
        // Remove any URL parameters
        if (imagePath.contains("?")) {
            imagePath = imagePath.split("\\?")[0];
        }
        
        return imagePath.isEmpty() ? null : imagePath;
    }
}