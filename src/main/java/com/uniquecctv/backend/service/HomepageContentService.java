package com.uniquecctv.backend.service;

import com.uniquecctv.backend.entity.HomepageContent;
import com.uniquecctv.backend.repository.HomepageContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomepageContentService {

    @Autowired
    private HomepageContentRepository homepageContentRepository;

    public HomepageContent getHomepageContent() {
        return homepageContentRepository.findFirstByOrderByIdAsc()
                .orElseGet(() -> createDefaultHomepageContent());
    }

    public HomepageContent updateHomepageContent(HomepageContent content) {
        HomepageContent existingContent = homepageContentRepository.findFirstByOrderByIdAsc()
                .orElse(new HomepageContent());

        // ✅ FIXED: No need to clean paths - frontend now sends only filenames
        // Update fields if provided
        if (content.getHeroImage() != null) {
            existingContent.setHeroImage(content.getHeroImage()); // Store as-is (filename)
        }
        if (content.getHeroTitle() != null) existingContent.setHeroTitle(content.getHeroTitle());
        if (content.getHeroSubtitle() != null) existingContent.setHeroSubtitle(content.getHeroSubtitle());
        if (content.getHeroButtonText() != null) existingContent.setHeroButtonText(content.getHeroButtonText());

        // Update slides
        if (content.getSlide1Image() != null) {
            existingContent.setSlide1Image(content.getSlide1Image()); // Store as-is (filename)
        }
        if (content.getSlide1Title() != null) existingContent.setSlide1Title(content.getSlide1Title());
        if (content.getSlide1Description() != null) existingContent.setSlide1Description(content.getSlide1Description());
        if (content.getSlide1ButtonText() != null) existingContent.setSlide1ButtonText(content.getSlide1ButtonText());

        if (content.getSlide2Image() != null) {
            existingContent.setSlide2Image(content.getSlide2Image()); // Store as-is (filename)
        }
        if (content.getSlide2Title() != null) existingContent.setSlide2Title(content.getSlide2Title());
        if (content.getSlide2Description() != null) existingContent.setSlide2Description(content.getSlide2Description());
        if (content.getSlide2ButtonText() != null) existingContent.setSlide2ButtonText(content.getSlide2ButtonText());

        if (content.getSlide3Image() != null) {
            existingContent.setSlide3Image(content.getSlide3Image()); // Store as-is (filename)
        }
        if (content.getSlide3Title() != null) existingContent.setSlide3Title(content.getSlide3Title());
        if (content.getSlide3Description() != null) existingContent.setSlide3Description(content.getSlide3Description());
        if (content.getSlide3ButtonText() != null) existingContent.setSlide3ButtonText(content.getSlide3ButtonText());

        if (content.getSlide4Image() != null) {
            existingContent.setSlide4Image(content.getSlide4Image()); // Store as-is (filename)
        }
        if (content.getSlide4Title() != null) existingContent.setSlide4Title(content.getSlide4Title());
        if (content.getSlide4Description() != null) existingContent.setSlide4Description(content.getSlide4Description());
        if (content.getSlide4ButtonText() != null) existingContent.setSlide4ButtonText(content.getSlide4ButtonText());

        return homepageContentRepository.save(existingContent);
    }

    // ✅ REMOVED: cleanImagePath() method - no longer needed
    // The frontend now sends only filenames, and DTO handles URL conversion

    private HomepageContent createDefaultHomepageContent() {
        HomepageContent defaultContent = new HomepageContent();
        
        // Set default hero content
        defaultContent.setHeroTitle("Welcome to Unique CCTV");
        defaultContent.setHeroSubtitle("Your trusted partner for advanced security and surveillance solutions. We provide high-quality CCTV systems, Alarm Monitoring, Biometrics & Access Control Systems, Networking.");
        defaultContent.setHeroButtonText("View Our Products");
        
        // Set default slides content
        defaultContent.setSlide1Title("Advanced Security Solutions");
        defaultContent.setSlide1Description("State-of-the-art CCTV systems for complete protection");
        defaultContent.setSlide1ButtonText("Explore Solutions");
        
        defaultContent.setSlide2Title("24/7 Monitoring");
        defaultContent.setSlide2Description("Round-the-clock surveillance for your peace of mind");
        defaultContent.setSlide2ButtonText("Learn More");
        
        defaultContent.setSlide3Title("Smart Access Control");
        defaultContent.setSlide3Description("Biometric and advanced access control systems");
        defaultContent.setSlide3ButtonText("Get Started");
        
        defaultContent.setSlide4Title("Professional Installation");
        defaultContent.setSlide4Description("Expert installation by certified security professionals");
        defaultContent.setSlide4ButtonText("Contact Us");
        
        return homepageContentRepository.save(defaultContent);
    }
}