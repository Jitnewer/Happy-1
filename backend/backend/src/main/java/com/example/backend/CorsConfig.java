package com.example.backend;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://localhost:*", getHostIPAddressPattern());
    }

    private String getHostIPAddressPattern() {
        // Implement your logic to get host IP address pattern
        // For example, return a specific pattern or dynamically calculate it.
        return "127.0.0.1";
    }
}
