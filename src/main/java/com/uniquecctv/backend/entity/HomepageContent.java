package com.uniquecctv.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "homepage_content")
@Data
public class HomepageContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hero_image")
    private String heroImage;

    @Column(name = "hero_title")
    private String heroTitle;

    @Column(name = "hero_subtitle", columnDefinition = "TEXT")
    private String heroSubtitle;

    @Column(name = "hero_button_text")
    private String heroButtonText;

    @Column(name = "slide1_image")
    private String slide1Image;

    @Column(name = "slide1_title")
    private String slide1Title;

    @Column(name = "slide1_description", columnDefinition = "TEXT")
    private String slide1Description;

    @Column(name = "slide1_button_text")
    private String slide1ButtonText;

    @Column(name = "slide2_image")
    private String slide2Image;

    @Column(name = "slide2_title")
    private String slide2Title;

    @Column(name = "slide2_description", columnDefinition = "TEXT")
    private String slide2Description;

    @Column(name = "slide2_button_text")
    private String slide2ButtonText;

    @Column(name = "slide3_image")
    private String slide3Image;

    @Column(name = "slide3_title")
    private String slide3Title;

    @Column(name = "slide3_description", columnDefinition = "TEXT")
    private String slide3Description;

    @Column(name = "slide3_button_text")
    private String slide3ButtonText;

    @Column(name = "slide4_image")
    private String slide4Image;

    @Column(name = "slide4_title")
    private String slide4Title;

    @Column(name = "slide4_description", columnDefinition = "TEXT")
    private String slide4Description;

    @Column(name = "slide4_button_text")
    private String slide4ButtonText;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

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
        
        // If it's already a full URL (external), return as is
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

}