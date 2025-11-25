package com.uniquecctv.backend.controller;

import com.uniquecctv.backend.dto.UserResponse;
import com.uniquecctv.backend.service.UserService;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        System.out.println("✅ UserController initialized - UserService: " + (userService != null ? "INJECTED" : "NULL"));
    }
    
    public UserController() {
        System.out.println("✅ UserController constructor called - Controller is instantiated!");
    }
    
    @GetMapping("/simple-test")
    public ResponseEntity<String> simpleTest() {
        System.out.println("✅ SIMPLE TEST METHOD CALLED!");
        return ResponseEntity.ok("Simple test working");
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Long>> getUserStats() {
        Map<String, Long> stats = Map.of(
            "totalUsers", userService.getUserCount(),
            "adminUsers", userService.getUserCountByRole("ADMIN"),
            "regularUsers", userService.getUserCountByRole("USER")
        );
        return ResponseEntity.ok(stats);
    }

    @PutMapping("/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUserRole(@PathVariable Long userId, @RequestBody Map<String, String> request) {
        System.out.println("🎯🎯🎯 UPDATE ROLE METHOD ENTERED!");
        System.out.println("📦 User ID: " + userId);
        System.out.println("📦 Request: " + request);
        
        try {
            System.out.println("🔍 Checking role in request...");
            String newRole = request.get("role");
            System.out.println("📦 Role from request: '" + newRole + "'");
            
            if (newRole == null) {
                System.out.println("❌ Role is null!");
                return ResponseEntity.badRequest().body("{\"error\": \"Role is required\"}");
            }
            
            System.out.println("🔍 Calling userService.updateUserRole(" + userId + ", " + newRole + ")");
            userService.updateUserRole(userId, newRole);
            System.out.println("✅ UserService call completed");
            
            System.out.println("✅ User role updated successfully!");
            return ResponseEntity.ok().body("{\"message\": \"User role updated successfully\"}");
            
        } catch (Exception e) {
            System.out.println("❌ EXCEPTION in updateUserRole: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("{\"error\": \"Failed: " + e.getMessage() + "\"}");
        } finally {
            System.out.println("🎯🎯🎯 UPDATE ROLE METHOD EXITING!");
        }
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        System.out.println("🎯 DELETE ENDPOINT HIT - User ID: " + id);
        System.out.println("🔍 Path Variable ID: " + id + " (Type: " + id.getClass().getSimpleName() + ")");
        
        try {
            userService.deleteUser(id);
            System.out.println("✅ User deleted successfully: " + id);
            return ResponseEntity.ok().body("{\"message\": \"User deleted successfully\"}");
        } catch (Exception e) {
            System.out.println("❌ Error deleting user: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Failed to delete user: " + e.getMessage() + "\"}");
        }
    }
}