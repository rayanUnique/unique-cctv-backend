package com.uniquecctv.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ContactResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String message;
    private LocalDateTime createdAt;
    private Boolean isRead;
}