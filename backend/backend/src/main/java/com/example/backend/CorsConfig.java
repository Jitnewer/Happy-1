package com.example.backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://ewa-frontend-2hlf.onrender.com", "https://ewa-backend-ianp.onrender.com", "http://localhost:8080", "http://localhost:8085")
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Add OPTIONS
                .allowedHeaders(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE)  // Allow all headers
                .exposedHeaders(HttpHeaders.AUTHORIZATION)
                .allowCredentials(true);

    }
}
