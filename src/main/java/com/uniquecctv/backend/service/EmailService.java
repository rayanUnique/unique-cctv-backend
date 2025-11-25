package com.uniquecctv.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.company.name:Unique CCTV}")
    private String companyName;

    public void sendAppointmentReply(String toEmail, String customerName, String appointmentDate, 
                                   String appointmentTime, String adminReply, String adminName) {
        try {
            logger.info("📧 EmailService: Starting email send to: {}", toEmail);
            logger.info("   From: {}, Customer: {}", fromEmail, customerName);
            
            // Validate inputs
            if (toEmail == null || toEmail.trim().isEmpty()) {
                throw new IllegalArgumentException("Recipient email cannot be null or empty");
            }
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail.trim());
            message.setSubject("Re: Your Appointment with " + companyName);
            
            logger.info("📝 Building email content...");
            String textContent = buildEmailContent(customerName, appointmentDate, appointmentTime, adminReply, adminName);
            message.setText(textContent);
            
            logger.info("✉️ Attempting to send email via JavaMailSender...");
            mailSender.send(message);
            logger.info("✅ Email sent successfully to: {}", toEmail);
            
        } catch (Exception e) {
            logger.error("❌ EmailService: Failed to send email to: {}", toEmail, e);
            throw new RuntimeException("Failed to send email to " + toEmail + ": " + e.getMessage(), e);
        }
    }

    private String buildEmailContent(String customerName, String appointmentDate, 
                                   String appointmentTime, String adminReply, String adminName) {
        String content = "Dear " + (customerName != null ? customerName : "Valued Customer") + ",\n\n" +
                        "Thank you for your appointment request with " + companyName + ".\n\n" +
                        "Appointment Details:\n" +
                        "Date: " + (appointmentDate != null ? appointmentDate : "Not specified") + "\n" +
                        "Time: " + (appointmentTime != null ? appointmentTime : "Not specified") + "\n\n" +
                        "Our Response:\n" +
                        (adminReply != null ? adminReply : "We will contact you shortly.") + "\n\n" +
                        "If you have any questions or need to reschedule, please contact us.\n\n" +
                        "Best regards,\n" +
                        (adminName != null ? adminName : "Admin") + "\n" +
                        companyName + " Team\n\n" +
                        "---\n" +
                        "This is an automated message.";
        
        logger.debug("📄 Email content built successfully");
        return content;
    }

    // Optional: Test method to verify email configuration
    public void testEmailConfiguration() {
        try {
            logger.info("🧪 Testing email configuration...");
            SimpleMailMessage testMessage = new SimpleMailMessage();
            testMessage.setFrom(fromEmail);
            testMessage.setTo(fromEmail); // Send test to yourself
            testMessage.setSubject("Test Email from " + companyName);
            testMessage.setText("This is a test email to verify the email configuration is working properly.");
            
            mailSender.send(testMessage);
            logger.info("✅ Test email sent successfully!");
        } catch (Exception e) {
            logger.error("❌ Test email failed: {}", e.getMessage(), e);
            throw new RuntimeException("Email configuration test failed: " + e.getMessage(), e);
        }
    }
}