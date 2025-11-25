package com.uniquecctv.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    @Column(nullable = false, length = 200)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Price is required")
    @Column(nullable = false)
    private Double price;

    @NotBlank(message = "Category is required")
    @Column(nullable = false, length = 100)
    private String category;

    // ✅ CORRECT: Stores only filename
    @Column(name = "image", length = 500)
    private String image;

    @Column(columnDefinition = "TEXT")
    private String specifications;

    @Column(name = "in_stock")
    private Boolean inStock = true;

    @Column(name = "stock_quantity")
    private Integer stockQuantity = 0;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // ✅ REMOVED: getImageUrl() method - URL construction belongs in frontend
    
    // ✅ REMOVED: getImageFilename() method - entity already stores filename directly
}