package com.cimbaai.emailassistant.service;

import com.cimbaai.emailassistant.dto.EmailReplyRequest;
import com.cimbaai.emailassistant.dto.EmailReplyResponse;
import com.cimbaai.emailassistant.model.EmailReply;
import com.cimbaai.emailassistant.repository.EmailReplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class EmailReplyService {

    private final EmailReplyRepository repository;
    private final GeminiAIService aiService;

    @Autowired
    public EmailReplyService(EmailReplyRepository repository, GeminiAIService aiService) {
        this.repository = repository;
        this.aiService = aiService;
    }

    public EmailReplyResponse generateAndSaveReply(EmailReplyRequest request) {
        try {
            log.info("Generating reply for email with tone: {}", request.getTone());

            // Generate reply using AI
            String generatedReply = aiService.generateReply(
                    request.getBody(),
                    request.getTone(),
                    request.getSender(),
                    request.getSubject()
            );

            // Create entity
            EmailReply emailReply = new EmailReply();
            emailReply.setSender(request.getSender());
            emailReply.setSubject(request.getSubject());
            emailReply.setBody(request.getBody());
            emailReply.setTone(request.getTone());
            emailReply.setGeneratedReply(generatedReply);

            // Save to database
            EmailReply saved = repository.save(emailReply);
            log.info("Saved email reply with ID: {}", saved.getId());

            // Convert to response DTO
            return EmailReplyResponse.builder()
                    .id(saved.getId())
                    .sender(saved.getSender())
                    .subject(saved.getSubject())
                    .body(saved.getBody())
                    .tone(saved.getTone())
                    .generatedReply(saved.getGeneratedReply())
                    .createdAt(saved.getCreatedAt())
                    .updatedAt(saved.getUpdatedAt())
                    .success(true)
                    .message("Reply generated successfully")
                    .build();

        } catch (Exception e) {
            log.error("Error generating reply", e);
            return EmailReplyResponse.builder()
                    .success(false)
                    .message("Failed to generate reply: " + e.getMessage())
                    .build();
        }
    }

    public List<EmailReplyResponse> getAllReplies() {
        List<EmailReply> replies = repository.findAllByOrderByCreatedAtDesc();
        return replies.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public EmailReplyResponse getReplyById(Long id) {
        return repository.findById(id)
                .map(this::convertToResponse)
                .orElse(null);
    }

    public List<EmailReplyResponse> getRecentReplies() {
        OffsetDateTime thirtyDaysAgo = OffsetDateTime.now().minusDays(30);
        List<EmailReply> replies = repository.findRecentReplies(thirtyDaysAgo);
        return replies.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private EmailReplyResponse convertToResponse(EmailReply entity) {
        return EmailReplyResponse.builder()
                .id(entity.getId())
                .sender(entity.getSender())
                .subject(entity.getSubject())
                .body(entity.getBody())
                .tone(entity.getTone())
                .generatedReply(entity.getGeneratedReply())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .success(true)
                .build();
    }
}