package com.uniquecctv.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
public class UniqueCctvBackendApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(UniqueCctvBackendApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 Application started successfully!");
        createAdminUser();
        createSampleUser();
        createSampleProducts();
        createDefaultHomepageContent();
    }
    
    @Bean
    public CommandLineRunner debugEndpoints(ApplicationContext context) {
        return args -> {
            System.out.println("\n🔍 ===== CHECKING REGISTERED ENDPOINTS =====");
            try {
                // Wait longer for all controllers to initialize
                Thread.sleep(5000);
                
                var mapping = context.getBean(RequestMappingHandlerMapping.class);
                var handlerMethods = mapping.getHandlerMethods();
                
                if (handlerMethods.isEmpty()) {
                    System.out.println("❌ NO ENDPOINTS FOUND!");
                } else {
                    System.out.println("✅ FOUND " + handlerMethods.size() + " ENDPOINTS:");
                    handlerMethods.forEach((key, value) -> {
                        if (key.getPatternsCondition() != null) {
                            key.getPatternsCondition().getPatterns().forEach(pattern -> {
                                System.out.println("   🌐 " + pattern + " -> " + value.getMethod().getName() + " in " + value.getBeanType().getSimpleName());
                            });
                        }
                    });
                }
                
                // Check if HomepageContentController is loaded
                System.out.println("\n🔍 ===== CHECKING CONTROLLERS =====");
                String[] beans = context.getBeanNamesForType(Object.class);
                for (String bean : beans) {
                    if (bean.toLowerCase().contains("homepage") || bean.toLowerCase().contains("controller")) {
                        System.out.println("✅ Bean: " + bean);
                    }
                }
                
            } catch (Exception e) {
                System.out.println("❌ Error checking endpoints: " + e.getMessage());
                e.printStackTrace();
            }
            System.out.println("========================================\n");
        };
    }
    
    private void createAdminUser() {
        System.out.println("👤 Creating admin user...");
    }
    
    private void createSampleUser() {
        System.out.println("👥 Creating sample user...");
    }
    
    private void createSampleProducts() {
        System.out.println("📦 Creating sample products...");
    }
    
    private void createDefaultHomepageContent() {
        System.out.println("🏠 Creating default homepage content...");
    }
}