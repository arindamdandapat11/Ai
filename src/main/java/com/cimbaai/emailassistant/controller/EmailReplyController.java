package com.cimbaai.emailassistant.controller;

import com.cimbaai.emailassistant.dto.EmailReplyRequest;
import com.cimbaai.emailassistant.dto.EmailReplyResponse;
import com.cimbaai.emailassistant.service.EmailReplyService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "${cors.allowed-origins}")
@Slf4j
public class EmailReplyController {

    private final EmailReplyService service;

    public EmailReplyController(EmailReplyService service) {
        this.service = service;
    }

    @PostMapping("/generate-reply")
    public ResponseEntity generateReply(
            @Valid @RequestBody EmailReplyRequest request) {

        log.info("Received request to generate reply with tone: {}", request.getTone());

        try {
            EmailReplyResponse response = service.generateAndSaveReply(request);

            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(response);
            }

        } catch (Exception e) {
            log.error("Error in generateReply endpoint", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(EmailReplyResponse.builder()
                            .success(false)
                            .message("Internal server error: " + e.getMessage())
                            .build());
        }
    }

    @GetMapping("/replies")
    public ResponseEntity<List> getAllReplies() {
        log.info("Fetching all email replies");
        List replies = service.getAllReplies();
        return ResponseEntity.ok(replies);
    }

    @GetMapping("/replies/recent")
    public ResponseEntity<List> getRecentReplies() {
        log.info("Fetching recent email replies (last 30 days)");
        List replies = service.getRecentReplies();
        return ResponseEntity.ok(replies);
    }

    @GetMapping("/replies/{id}")
    public ResponseEntity getReplyById(@PathVariable Long id) {
        log.info("Fetching email reply with ID: {}", id);
        EmailReplyResponse response = service.getReplyById(id);

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity healthCheck() {
        return ResponseEntity.ok("Email Assistant API is running!");
    }
}