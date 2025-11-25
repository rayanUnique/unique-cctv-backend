package com.uniquecctv.backend.controller;

import com.uniquecctv.backend.dto.ContactDTO;
import com.uniquecctv.backend.dto.ContactResponseDTO;
import com.uniquecctv.backend.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<?> createContact(@Valid @RequestBody ContactDTO contactDTO) {
        try {
            System.out.println("📨 Received contact form submission:");
            System.out.println("   Name: " + contactDTO.getName());
            System.out.println("   Email: " + contactDTO.getEmail());
            System.out.println("   Phone: " + contactDTO.getPhone());
            System.out.println("   Message: " + contactDTO.getMessage());

            ContactResponseDTO response = contactService.createContact(contactDTO);
            
            System.out.println("✅ Contact saved successfully with ID: " + response.getId());

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Thank you for your message! We'll get back to you soon.");
            responseBody.put("contact", response);
            responseBody.put("success", true);

            return ResponseEntity.ok(responseBody);

        } catch (Exception e) {
            System.err.println("❌ Error in ContactController: " + e.getMessage());
            e.printStackTrace();

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Failed to submit your message. Please try again.");
            errorResponse.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<List<ContactResponseDTO>> getAllContacts() {
        try {
            List<ContactResponseDTO> contacts = contactService.getAllContacts();
            System.out.println("📋 Retrieved " + contacts.size() + " contacts");
            return ResponseEntity.ok(contacts);
        } catch (Exception e) {
            System.err.println("❌ Error fetching contacts: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactResponseDTO> getContactById(@PathVariable Long id) {
        try {
            ContactResponseDTO contact = contactService.getContactById(id);
            return ResponseEntity.ok(contact);
        } catch (RuntimeException e) {
            System.err.println("❌ Contact not found with ID: " + id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("❌ Error fetching contact: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<ContactResponseDTO> markAsRead(@PathVariable Long id) {
        try {
            ContactResponseDTO contact = contactService.markAsRead(id);
            System.out.println("📭 Marked contact as read: " + id);
            return ResponseEntity.ok(contact);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long id) {
        try {
            contactService.deleteContact(id);
            System.out.println("🗑️ Deleted contact: " + id);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Contact deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/unread")
    public ResponseEntity<List<ContactResponseDTO>> getUnreadContacts() {
        try {
            List<ContactResponseDTO> contacts = contactService.getUnreadContacts();
            System.out.println("📬 Retrieved " + contacts.size() + " unread contacts");
            return ResponseEntity.ok(contacts);
        } catch (Exception e) {
            System.err.println("❌ Error fetching unread contacts: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/unread/count")
    public ResponseEntity<Map<String, Long>> getUnreadCount() {
        try {
            Long count = contactService.getUnreadCount();
            Map<String, Long> response = new HashMap<>();
            response.put("count", count);
            System.out.println("🔢 Unread contacts count: " + count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("❌ Error counting unread contacts: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}