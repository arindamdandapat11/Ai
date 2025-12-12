package com.cimbaai.emailassistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
}