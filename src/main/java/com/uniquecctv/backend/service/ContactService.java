package com.uniquecctv.backend.service;

import com.uniquecctv.backend.dto.ContactDTO;
import com.uniquecctv.backend.dto.ContactResponseDTO;
import com.uniquecctv.backend.entity.Contact;
import com.uniquecctv.backend.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public ContactResponseDTO createContact(ContactDTO contactDTO) {
        // Convert DTO to Entity
        Contact contact = new Contact();
        contact.setName(contactDTO.getName());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhone(contactDTO.getPhone());
        contact.setMessage(contactDTO.getMessage());
        contact.setIsRead(false); // Default to unread

        System.out.println("💾 Saving contact to database:");
        System.out.println("   Name: " + contact.getName());
        System.out.println("   Email: " + contact.getEmail());
        System.out.println("   Phone: " + contact.getPhone());
        System.out.println("   Message: " + contact.getMessage());

        Contact savedContact = contactRepository.save(contact);
        
        System.out.println("✅ Contact saved successfully with ID: " + savedContact.getId());
        
        return convertToResponseDTO(savedContact);
    }

    public List<ContactResponseDTO> getAllContacts() {
        List<Contact> contacts = contactRepository.findAllByOrderByCreatedAtDesc();
        return contacts.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public ContactResponseDTO getContactById(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));
        return convertToResponseDTO(contact);
    }

    public ContactResponseDTO markAsRead(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));
        contact.setIsRead(true);
        Contact updatedContact = contactRepository.save(contact);
        return convertToResponseDTO(updatedContact);
    }

    public void deleteContact(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new RuntimeException("Contact not found with id: " + id);
        }
        contactRepository.deleteById(id);
    }

    public List<ContactResponseDTO> getUnreadContacts() {
        List<Contact> contacts = contactRepository.findByIsReadFalseOrderByCreatedAtDesc();
        return contacts.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public Long getUnreadCount() {
        return contactRepository.countByIsReadFalse();
    }

    private ContactResponseDTO convertToResponseDTO(Contact contact) {
        ContactResponseDTO responseDTO = new ContactResponseDTO();
        responseDTO.setId(contact.getId());
        responseDTO.setName(contact.getName());
        responseDTO.setEmail(contact.getEmail());
        responseDTO.setPhone(contact.getPhone());
        responseDTO.setMessage(contact.getMessage());
        responseDTO.setCreatedAt(contact.getCreatedAt());
        responseDTO.setIsRead(contact.getIsRead());
        return responseDTO;
    }
}