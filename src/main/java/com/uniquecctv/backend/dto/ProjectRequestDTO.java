package com.uniquecctv.backend.dto;

public class ProjectRequestDTO {
    private String title;
    private String type;
    private String description;
    private String image;
    
    // Constructors
    public ProjectRequestDTO() {}
    
    public ProjectRequestDTO(String title, String type, String description, String image) {
        this.title = title;
        this.type = type;
        this.description = description;
        this.image = image;
    }
    
    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    
    @Override
    public String toString() {
        return "ProjectRequestDTO{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}