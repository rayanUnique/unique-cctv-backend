package com.uniquecctv.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String role; // Keep single role for backward compatibility
    private List<String> roles; // ADD THIS for multiple roles
    
    public UserResponse(Long id, String name, String email, String phone, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }
    
    // ADD new constructor with roles
    public UserResponse(Long id, String name, String email, String phone, String role, List<String> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.roles = roles;
    }
}