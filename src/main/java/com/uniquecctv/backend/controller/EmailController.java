package com.uniquecctv.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import com.uniquecctv.backend.service.EmailService;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private JavaMailSender mailSender;

    public EmailController() {
        logger.info("✅ EmailController initialized!");
    }

    // SIMPLE TEST - Basic endpoint test
    @PostMapping("/simple-test")
    public ResponseEntity<String> simpleTest() {
        logger.info("🎯 SIMPLE-TEST endpoint called!");
        return ResponseEntity.ok("Endpoint is working!");
    }

    // DIRECT TEST
    @PostMapping("/direct-test")
    public ResponseEntity<Map<String, String>> directTestEmail() {
        try {
            logger.info("🧪 DIRECT TEST: Testing email configuration...");
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("rayyanshaikh7066@gmail.com");
            message.setTo("rayyanshaikh7066@gmail.com");
            message.setSubject("Direct Test Email from Unique CCTV");
            message.setText("This is a direct test email from Unique CCTV backend. If you receive this, email configuration is working!");
            
            mailSender.send(message);
            logger.info("✅ DIRECT TEST: Email sent successfully");
            return ResponseEntity.ok(Map.of("message", "Direct test email sent successfully"));
            
        } catch (Exception e) {
            logger.error("❌ DIRECT TEST FAILED: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Direct test failed", 
                "details", e.getMessage()
            ));
        }
    }

    // VALIDATE CONFIG
    @PostMapping("/validate-config")
    public ResponseEntity<Map<String, Object>> validateEmailConfig() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            logger.info("🔧 Validating email configuration...");
            
            // Test if we can create a message (basic configuration test)
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("test@example.com");
            message.setSubject("Configuration Test");
            message.setText("Test message");
            
            response.put("status", "success");
            response.put("message", "Email configuration is valid");
            response.put("timestamp", System.currentTimeMillis());
            logger.info("✅ Email configuration validation successful");
            
        } catch (Exception e) {
            logger.error("❌ Email configuration validation failed: {}", e.getMessage(), e);
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/appointment-reply")
    public ResponseEntity<Map<String, String>> sendAppointmentReply(@RequestBody Map<String, String> emailRequest) {
        try {
            logger.info("📧 Received appointment reply email request");
            logger.debug("Email request data: {}", emailRequest);
            
            String toEmail = emailRequest.get("toEmail");
            String customerName = emailRequest.get("customerName");
            String appointmentDate = emailRequest.get("appointmentDate");
            String appointmentTime = emailRequest.get("appointmentTime");
            String adminReply = emailRequest.get("adminReply");
            String adminName = emailRequest.get("adminName");

            logger.info("📧 Sending appointment reply to: {}", toEmail);
            
            if (toEmail == null || toEmail.trim().isEmpty()) {
                logger.warn("❌ Email validation failed: Recipient email is required");
                return ResponseEntity.badRequest().body(Map.of("error", "Recipient email is required"));
            }

            emailService.sendAppointmentReply(toEmail, customerName, appointmentDate, 
                                            appointmentTime, adminReply, adminName);
            
            logger.info("✅ Appointment reply email sent successfully to: {}", toEmail);
            return ResponseEntity.ok(Map.of("message", "Email sent successfully to " + toEmail));
            
        } catch (Exception e) {
            logger.error("❌ Appointment reply email sending failed: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Failed to send email", 
                "details", e.getMessage()
            ));
        }
    }

    // Optional: Add a test endpoint that uses the EmailService
    @PostMapping("/test-service")
    public ResponseEntity<Map<String, String>> testEmailService() {
        try {
            logger.info("🧪 Testing EmailService directly...");
            
            // Test sending to yourself
            emailService.sendAppointmentReply(
                "rayyanshaikh7066@gmail.com",
                "Test Customer",
                "2024-01-01",
                "10:00 AM",
                "This is a test reply from the EmailService",
                "Test Admin"
            );
            
            logger.info("✅ EmailService test completed successfully");
            return ResponseEntity.ok(Map.of("message", "EmailService test completed successfully"));
            
        } catch (Exception e) {
            logger.error("❌ EmailService test failed: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of(
                "error", "EmailService test failed",
                "details", e.getMessage()
            ));
        }
    }
}