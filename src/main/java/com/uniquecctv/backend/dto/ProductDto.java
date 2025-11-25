package com.uniquecctv.backend.dto;

import com.uniquecctv.backend.entity.Product;
import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private String image; // ✅ CORRECT: Stores filename only
    private String specifications;
    private Boolean inStock;
    private Integer stockQuantity;

    // ✅ CORRECT: Default constructor
    public ProductDto() {
    }

    // ✅ CORRECT: Constructor from Entity
    public ProductDto(Product entity) {
        if (entity == null) {
            return;
        }
        
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.category = entity.getCategory();
        this.image = entity.getImage(); // ✅ Direct field access (filename only)
        this.specifications = entity.getSpecifications();
        this.inStock = entity.getInStock();
        this.stockQuantity = entity.getStockQuantity();
    }

    // ✅ REMOVED: getImageUrl() - URL construction belongs in frontend only
    
    // ✅ REMOVED: getImageFilename() - DTO already returns filename directly

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", image='" + image + '\'' + // ✅ CORRECT: raw filename
                ", specifications='" + specifications + '\'' +
                ", inStock=" + inStock +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}