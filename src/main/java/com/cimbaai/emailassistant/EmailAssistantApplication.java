package com.cimbaai.emailassistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Main Application Class for CIMBA AI Email Assistant
 *
 * This Spring Boot application provides AI-powered email reply generation
 * using Google's Gemini 1.5 Pro model and PostgreSQL for data persistence.
 *
 * Features:
 * - Professional, Friendly, and Concise tone options
 * - Email reply history storage
 * - RESTful API endpoints
 * - Integration with Google Gemini AI
 *
 * @author Arindam Dandapat
 * @version 1.0
 */
@SpringBootApplication
public class EmailAssistantApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailAssistantApplication.class, args);
        System.out.println("""
            
            ╔══════════════════════════════════════════════════════════════╗
            ║                                                              ║
            ║   🤖 CIMBA AI Email Assistant - Backend Server Started 🤖   ║
            ║                                                              ║
            ║   Server running at: http://localhost:8080                   ║
            ║   API Docs (Swagger): http://localhost:8080/swagger-ui.html  ║
            ║   Health Check: http://localhost:8080/api/email/health       ║
            ║                                                              ║
            ║   Ready to generate intelligent email replies! ✨           ║
            ║                                                              ║
            ╚══════════════════════════════════════════════════════════════╝
            """);
    }

    /**
     * Bean definition for RestTemplate
     * Used for making HTTP requests to external APIs (Gemini AI)
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
