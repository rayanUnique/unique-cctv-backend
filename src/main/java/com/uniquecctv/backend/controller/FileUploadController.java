package com.uniquecctv.backend.controller;

import com.uniquecctv.backend.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    // ✅ REMOVED: baseUrl - URL construction belongs in frontend

    // ADD THIS DEBUG ENDPOINT (keep as is)
    @GetMapping("/debug-auth")
    public ResponseEntity<?> debugAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        Map<String, Object> response = new HashMap<>();
        response.put("authenticated", auth != null && auth.isAuthenticated());
        response.put("username", auth != null ? auth.getName() : "null");
        response.put("authorities", auth != null ? auth.getAuthorities().toString() : "null");
        response.put("principal", auth != null ? auth.getPrincipal().getClass().getSimpleName() : "null");
        response.put("hasAdminRole", auth != null && auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")));
        
        System.out.println("=== DEBUG AUTH ===");
        System.out.println("Authenticated: " + response.get("authenticated"));
        System.out.println("Username: " + response.get("username"));
        System.out.println("Authorities: " + response.get("authorities"));
        System.out.println("Has ADMIN role: " + response.get("hasAdminRole"));
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/image")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        System.out.println("=== 🎯 UPLOAD CONTROLLER CALLED ===");
    
        // Debug authentication
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("🔐 Authentication: " + auth);
        System.out.println("👤 Principal: " + (auth != null ? auth.getPrincipal() : "null"));
        System.out.println("🎯 Authorities: " + (auth != null ? auth.getAuthorities() : "null"));
        System.out.println("🔑 Is Authenticated: " + (auth != null ? auth.isAuthenticated() : "false"));
    
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            // Validate file type
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest().body("Only image files are allowed");
            }

            System.out.println("📁 Processing file: " + file.getOriginalFilename());
            System.out.println("📊 File size: " + file.getSize());

            // Store file
            String fileName = fileStorageService.storeFile(file);
            
            // ✅ CORRECTED: Return ONLY filename (consistent with hero image pattern)
            Map<String, String> response = new HashMap<>();
            response.put("fileName", fileName); // ✅ Only filename, no URL
            response.put("message", "File uploaded successfully");

            System.out.println("✅ Upload successful: " + fileName);
            
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.out.println("❌ Upload failed: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Could not upload file: " + e.getMessage());
        }
    }
    
    @GetMapping("/test")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> testEndpoint() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(Map.of(
            "message", "Test endpoint works!",
            "user", auth.getName(),
            "authorities", auth.getAuthorities().toString(),
            "authenticated", auth.isAuthenticated()
        ));
    }
}