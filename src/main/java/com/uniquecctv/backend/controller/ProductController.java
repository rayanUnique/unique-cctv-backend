package com.uniquecctv.backend.controller;

import com.uniquecctv.backend.dto.ProductDto;
import com.uniquecctv.backend.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {

	@ModelAttribute
    public void logRequest(HttpServletRequest request) {
        System.out.println("🎯 INCOMING REQUEST: " + request.getMethod() + " " + request.getRequestURI());
        System.out.println("📦 Content-Type: " + request.getContentType());
    }
	
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        System.out.println("🟢 RECEIVED FROM FRONTEND:");
        System.out.println("Stock: " + productDto.getStockQuantity());
        System.out.println("Price: " + productDto.getPrice());
        System.out.println("Name: " + productDto.getName());
        System.out.println("🖼️ Image: " + productDto.getImage()); // ✅ CORRECT: Log image field only
        
        // Log the entire DTO
        System.out.println("Full DTO: " + productDto.toString());
        
        ProductDto result = productService.createProduct(productDto);
        
        System.out.println("📦 RETURNED FROM SERVICE:");
        System.out.println("Stock: " + result.getStockQuantity());
        System.out.println("🖼️ Image after save: " + result.getImage()); // ✅ CORRECT: filename only
        
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable("id") Long id, 
            @RequestBody ProductDto productDto) {
        
        System.out.println("🎯 UPDATE PRODUCT ENDPOINT HIT!");
        System.out.println("📦 Product ID: " + id);
        
        try {
            System.out.println("📋 Received DTO: " + productDto);
            System.out.println("🔢 Stock Quantity: " + productDto.getStockQuantity());
            System.out.println("💰 Price: " + productDto.getPrice());
            System.out.println("🖼️ Image: " + productDto.getImage()); // ✅ CORRECT: filename only
            
            ProductDto result = productService.updateProduct(id, productDto);
            System.out.println("✅ Update successful: " + result);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            System.out.println("❌ CONTROLLER EXCEPTION:");
            System.out.println("   Error: " + e.getClass().getSimpleName());
            System.out.println("   Message: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw to see in global handler
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        System.out.println("✅ PRODUCT CONTROLLER TEST ENDPOINT HIT!");
        return ResponseEntity.ok("Product controller is working!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    // ✅ CORRECTED: Debug endpoint to check image data (consistent with pattern)
    @GetMapping("/{id}/image-debug")
    public ResponseEntity<?> debugProductImage(@PathVariable Long id) {
        try {
            ProductDto product = productService.getProductById(id);
            System.out.println("🔍 PRODUCT IMAGE DEBUG:");
            System.out.println("   Product ID: " + product.getId());
            System.out.println("   Product Name: " + product.getName());
            System.out.println("   Image field (filename): " + product.getImage()); // ✅ CORRECT: filename only
            
            return ResponseEntity.ok().body(
                "Image Debug - Filename: '" + product.getImage() + "'"
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}