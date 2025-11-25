package com.uniquecctv.backend.config;

import com.uniquecctv.backend.entity.HomepageContent;
import com.uniquecctv.backend.entity.Product;
import com.uniquecctv.backend.entity.User;
import com.uniquecctv.backend.repository.ProductRepository;
import com.uniquecctv.backend.repository.UserRepository;
import com.uniquecctv.backend.service.HomepageContentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private HomepageContentService homepageContentService;

    @Override
    public void run(String... args) throws Exception {
        createAdminUser();
        createSampleUser();
        createSampleProducts();
        createDefaultHomepageContent();
    }

    private void createAdminUser() {
        // Check if admin user already exists
        if (userRepository.findByEmail("admin@uniquecctv.com").isEmpty()) {
            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail("admin@uniquecctv.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            admin.setPhone("+1234567890");
            admin.setIsActive(true);
            
            userRepository.save(admin);
            System.out.println("✅ Admin user created successfully!");
            System.out.println("📧 Email: admin@uniquecctv.com");
            System.out.println("🔑 Password: admin123");
            System.out.println("👤 Role: ADMIN");
        } else {
            System.out.println("ℹ️ Admin user already exists");
        }
    }

    private void createSampleUser() {
        // Check if sample user already exists
        if (userRepository.findByEmail("user@example.com").isEmpty()) {
            User user = new User();
            user.setName("Demo User");
            user.setEmail("user@example.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole("USER");
            user.setPhone("+0987654321");
            user.setIsActive(true);
            
            userRepository.save(user);
            System.out.println("✅ Demo user created successfully!");
            System.out.println("📧 Email: user@example.com");
            System.out.println("🔑 Password: user123");
            System.out.println("👤 Role: USER");
        } else {
            System.out.println("ℹ️ Demo user already exists");
        }
    }

    private void createSampleProducts() {
        // Check if products already exist
        if (productRepository.count() == 0) {
            // Sample Product 1: Dome Camera
            Product product1 = new Product();
            product1.setName("4K Dome Camera");
            product1.setDescription("High-resolution 4K dome camera for indoor surveillance with night vision and wide dynamic range. Perfect for offices, homes, and commercial spaces.");
            product1.setPrice(8999.0);
            product1.setCategory("dome");
            product1.setImage("dome-camera-4k.jpg"); // ✅ CHANGED: setImage instead of setImageUrl
            product1.setSpecifications("4K Ultra HD, Night Vision up to 30m, Weatherproof IP67, Motion Detection, Two-Way Audio");
            product1.setInStock(true);
            product1.setStockQuantity(25);

            // Sample Product 2: Bullet Camera
            Product product2 = new Product();
            product2.setName("Weatherproof Bullet Camera");
            product2.setDescription("Outdoor bullet camera with weatherproof housing, ideal for perimeter security and outdoor monitoring in all weather conditions.");
            product2.setPrice(7599.0);
            product2.setCategory("bullet");
            product2.setImage("bullet-camera-outdoor.jpg"); // ✅ CHANGED
            product2.setSpecifications("1080p Full HD, IP66 Weatherproof, 100ft Night Vision, Metal Housing, Vandal Resistant");
            product2.setInStock(true);
            product2.setStockQuantity(30);

            // Sample Product 3: PTZ Camera
            Product product3 = new Product();
            product3.setName("360° PTZ Camera");
            product3.setDescription("Professional Pan-Tilt-Zoom camera with 360° coverage, auto tracking, and preset positions for comprehensive area monitoring.");
            product3.setPrice(12999.0);
            product3.setCategory("ptz");
            product3.setImage("ptz-camera-360.jpg"); // ✅ CHANGED
            product3.setSpecifications("360° Coverage, 30x Optical Zoom, Auto Tracking, 256 Preset Positions, IP67 Rated");
            product3.setInStock(true);
            product3.setStockQuantity(15);

            // Sample Product 4: Wireless IP Camera
            Product product4 = new Product();
            product4.setName("Wireless IP Camera");
            product4.setDescription("Wireless IP camera with mobile app access, cloud storage, and easy installation. Perfect for home security and small businesses.");
            product4.setPrice(6599.0);
            product4.setCategory("wireless");
            product4.setImage("wireless-ip-camera.jpg"); // ✅ CHANGED
            product4.setSpecifications("Wireless Connectivity, Mobile App, Cloud Storage, Two-Way Audio, Easy Installation");
            product4.setInStock(true);
            product4.setStockQuantity(40);

            // Sample Product 5: IP Camera
            Product product5 = new Product();
            product5.setName("4MP IP Camera");
            product5.setDescription("High-quality 4MP IP camera with PoE support, perfect for professional surveillance systems and network-based security.");
            product5.setPrice(5599.0);
            product5.setCategory("ip");
            product5.setImage("ip-camera-4mp.jpg"); // ✅ CHANGED
            product5.setSpecifications("4MP Resolution, Power over Ethernet, H.265 Compression, Remote Access, Built-in Microphone");
            product5.setInStock(true);
            product5.setStockQuantity(20);

            // Sample Product 6: Mini Dome Camera
            Product product6 = new Product();
            product6.setName("Mini Dome Camera");
            product6.setDescription("Compact dome camera with discreet design, suitable for indoor spaces where aesthetics matter without compromising security.");
            product6.setPrice(4999.0);
            product6.setCategory("dome");
            product6.setImage("mini-dome-camera.jpg"); // ✅ CHANGED
            product6.setSpecifications("Compact Design, 1080p HD, Discreet, Wide Angle Lens, Easy Installation");
            product6.setInStock(true);
            product6.setStockQuantity(35);

            // Save all products
            productRepository.saveAll(Arrays.asList(
                product1, product2, product3, product4, product5, product6
            ));

            System.out.println("✅ Sample products created successfully!");
            System.out.println("📦 Total products: " + productRepository.count());
            System.out.println("🎯 Categories: Dome, Bullet, PTZ, Wireless, IP");
            
            // ✅ ADDED: Debug output to verify image storage
            System.out.println("🖼️ Product images stored as filenames:");
            productRepository.findAll().forEach(product -> {
                System.out.println("   - " + product.getName() + ": " + product.getImage());
            });
        } else {
            System.out.println("ℹ️ Products already exist in database");
            System.out.println("📦 Total products: " + productRepository.count());
            
            // ✅ ADDED: Show current image storage format
            System.out.println("🖼️ Current product image storage:");
            productRepository.findAll().forEach(product -> {
                System.out.println("   - " + product.getName() + ": " + product.getImage());
            });
        }
    }
    
    private void createDefaultHomepageContent() {
        try {
            HomepageContent defaultContent = homepageContentService.getHomepageContent();
            System.out.println("✅ Homepage content initialized");
        } catch (Exception e) {
            System.out.println("⚠️ Homepage content initialization: " + e.getMessage());
        }
    }
}