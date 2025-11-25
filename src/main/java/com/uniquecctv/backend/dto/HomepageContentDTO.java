package com.uniquecctv.backend.dto;

import com.uniquecctv.backend.entity.HomepageContent;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HomepageContentDTO {
    private Long id;
    private String heroImage;
    private String heroTitle;
    private String heroSubtitle;
    private String heroButtonText;
    private String slide1Image;
    private String slide1Title;
    private String slide1Description;
    private String slide1ButtonText;
    private String slide2Image;
    private String slide2Title;
    private String slide2Description;
    private String slide2ButtonText;
    private String slide3Image;
    private String slide3Title;
    private String slide3Description;
    private String slide3ButtonText;
    private String slide4Image;
    private String slide4Title;
    private String slide4Description;
    private String slide4ButtonText;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public HomepageContentDTO() {
        // Default constructor
    }

    public HomepageContentDTO(HomepageContent entity) {
        if (entity == null) {
            return;
        }
        
        this.id = entity.getId();
        
        // ✅ FIXED: Use direct fields only - no URL methods!
        this.heroImage = entity.getHeroImage(); // Raw filename from database
        this.heroTitle = entity.getHeroTitle();
        this.heroSubtitle = entity.getHeroSubtitle();
        this.heroButtonText = entity.getHeroButtonText();
        
        this.slide1Image = entity.getSlide1Image(); // Raw filename from database
        this.slide1Title = entity.getSlide1Title();
        this.slide1Description = entity.getSlide1Description();
        this.slide1ButtonText = entity.getSlide1ButtonText();
        
        this.slide2Image = entity.getSlide2Image(); // Raw filename from database
        this.slide2Title = entity.getSlide2Title();
        this.slide2Description = entity.getSlide2Description();
        this.slide2ButtonText = entity.getSlide2ButtonText();
        
        this.slide3Image = entity.getSlide3Image(); // Raw filename from database
        this.slide3Title = entity.getSlide3Title();
        this.slide3Description = entity.getSlide3Description();
        this.slide3ButtonText = entity.getSlide3ButtonText();
        
        this.slide4Image = entity.getSlide4Image(); // Raw filename from database
        this.slide4Title = entity.getSlide4Title();
        this.slide4Description = entity.getSlide4Description();
        this.slide4ButtonText = entity.getSlide4ButtonText();
        
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
    
    // ✅ Helper methods to get full URLs for frontend
    public String getHeroImageUrl() {
        return convertToImageUrl(heroImage);
    }
    
    public String getSlide1ImageUrl() {
        return convertToImageUrl(slide1Image);
    }
    
    public String getSlide2ImageUrl() {
        return convertToImageUrl(slide2Image);
    }
    
    public String getSlide3ImageUrl() {
        return convertToImageUrl(slide3Image);
    }
    
    public String getSlide4ImageUrl() {
        return convertToImageUrl(slide4Image);
    }
    
    private String convertToImageUrl(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            return "";
        }
        
        // If it's already a full URL, return as is
        if (imagePath.startsWith("http://") || 
            imagePath.startsWith("https://") || 
            imagePath.startsWith("blob:") ||
            imagePath.startsWith("data:")) {
            return imagePath;
        }
        
        // If it already starts with /api/images/, return as is
        if (imagePath.startsWith("/api/images/")) {
            return imagePath;
        }
        
        // If it's just a file name, add the API prefix
        return "/api/images/" + imagePath;
    }
    
    // ✅ Helper method to get just the filename for admin purposes
    public String getHeroImageFilename() {
        return extractFilename(heroImage);
    }
    
    public String getSlide1ImageFilename() {
        return extractFilename(slide1Image);
    }
    
    public String getSlide2ImageFilename() {
        return extractFilename(slide2Image);
    }
    
    public String getSlide3ImageFilename() {
        return extractFilename(slide3Image);
    }
    
    public String getSlide4ImageFilename() {
        return extractFilename(slide4Image);
    }
    
    private String extractFilename(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            return "";
        }
        
        // For external URLs, extract filename from URL
        if (isExternalUrl(imagePath)) {
            String[] parts = imagePath.split("/");
            return parts[parts.length - 1];
        }
        
        // For local paths, extract filename
        if (imagePath.contains("/")) {
            String[] parts = imagePath.split("/");
            return parts[parts.length - 1];
        }
        
        // Already a filename
        return imagePath;
    }
    
    // ✅ Helper methods to check if image is external
    public boolean isHeroImageExternal() {
        return isExternalUrl(heroImage);
    }
    
    public boolean isSlide1ImageExternal() {
        return isExternalUrl(slide1Image);
    }
    
    public boolean isSlide2ImageExternal() {
        return isExternalUrl(slide2Image);
    }
    
    public boolean isSlide3ImageExternal() {
        return isExternalUrl(slide3Image);
    }
    
    public boolean isSlide4ImageExternal() {
        return isExternalUrl(slide4Image);
    }
    
    private boolean isExternalUrl(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            return false;
        }
        
        return imagePath.startsWith("http://") || 
               imagePath.startsWith("https://") || 
               imagePath.startsWith("blob:") ||
               imagePath.startsWith("data:");
    }
}