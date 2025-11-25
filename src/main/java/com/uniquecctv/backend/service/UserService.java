package com.uniquecctv.backend.service;

import com.uniquecctv.backend.dto.RegisterRequest;
import com.uniquecctv.backend.dto.UserResponse;
import com.uniquecctv.backend.entity.User;
import com.uniquecctv.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setPhone(registerRequest.getPhone());
        user.setRole("USER");

        return userRepository.save(user);
    }
    
    public void updateUserRole(Long userId, String newRole) {
        System.out.println("🔍 UserService.updateUserRole() called - User ID: " + userId + ", New Role: " + newRole);
        
        try {
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
            
            System.out.println("📋 Found user: " + user.getEmail() + ", Current role: " + user.getRole());
            
            // Update the role
            user.setRole(newRole);
            userRepository.save(user);
            
            System.out.println("✅ User role updated in database: " + user.getEmail() + " -> " + newRole);
            
        } catch (Exception e) {
            System.out.println("❌ Error in UserService.updateUserRole: " + e.getMessage());
            throw e;
        }
    }

    public UserResponse getUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        );
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::getUserResponse)
                .collect(Collectors.toList());
    }
    
    public void deleteUser(Long id) {
        System.out.println("🔍 Deleting user with ID: " + id);
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        // Optional: Add safety checks (prevent self-deletion, etc.)
        userRepository.delete(user);
        System.out.println("✅ User deleted: " + id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public long getUserCount() {
        return userRepository.count();
    }

    public long getUserCountByRole(String role) {
        return userRepository.countByRole(role);
    }
}