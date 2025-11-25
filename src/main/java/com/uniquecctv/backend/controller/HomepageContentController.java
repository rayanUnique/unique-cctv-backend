package com.uniquecctv.backend.controller;

import com.uniquecctv.backend.dto.HomepageContentDTO;
import com.uniquecctv.backend.entity.HomepageContent;
import com.uniquecctv.backend.service.HomepageContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/homepage")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HomepageContentController {

    @Autowired
    private HomepageContentService homepageContentService;

    @GetMapping
    public ResponseEntity<HomepageContentDTO> getHomepageContent() {
        HomepageContent content = homepageContentService.getHomepageContent();
        HomepageContentDTO dto = new HomepageContentDTO(content);
        return ResponseEntity.ok(dto);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HomepageContentDTO> updateHomepageContent(@RequestBody HomepageContent content) {
        HomepageContent updatedContent = homepageContentService.updateHomepageContent(content);
        HomepageContentDTO dto = new HomepageContentDTO(updatedContent);
        return ResponseEntity.ok(dto);
    }
}