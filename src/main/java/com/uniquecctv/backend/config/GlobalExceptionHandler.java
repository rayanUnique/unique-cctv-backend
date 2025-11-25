package com.uniquecctv.backend.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception ex, HttpServletRequest request) {
        System.out.println("🚨 GLOBAL EXCEPTION HANDLER:");
        System.out.println("   Request: " + request.getMethod() + " " + request.getRequestURI());
        System.out.println("   Exception: " + ex.getClass().getSimpleName());
        System.out.println("   Message: " + ex.getMessage());
        ex.printStackTrace();
        return "Error: " + ex.getMessage();
    }

    // Add this method for validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        
        System.out.println("🚨 VALIDATION EXCEPTION:");
        System.out.println("   Request: " + request.getMethod() + " " + request.getRequestURI());
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            System.out.println("   Validation error - " + fieldName + ": " + errorMessage);
        });
        
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Add this for specific runtime exceptions (like not found)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(
            RuntimeException ex, HttpServletRequest request) {
        
        System.out.println("🚨 RUNTIME EXCEPTION:");
        System.out.println("   Request: " + request.getMethod() + " " + request.getRequestURI());
        System.out.println("   Message: " + ex.getMessage());
        
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}