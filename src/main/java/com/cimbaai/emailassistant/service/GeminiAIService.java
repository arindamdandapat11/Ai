package com.cimbaai.emailassistant.service;

import com.cimbaai.emailassistant.dto.GeminiRequest;
import com.cimbaai.emailassistant.dto.GeminiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeminiAIService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public String generateReply(String emailBody, String tone, String sender, String subject) {
        try {
            String prompt = buildPrompt(emailBody, tone, sender, subject);

            GeminiRequest.Part part = new GeminiRequest.Part(prompt);
            GeminiRequest.Content content = new GeminiRequest.Content(Collections.singletonList(part));
            GeminiRequest request = new GeminiRequest(Collections.singletonList(content));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String url = apiUrl + "?key=" + apiKey;
            HttpEntity<GeminiRequest> entity = new HttpEntity<>(request, headers);

            log.info("Calling Gemini API for {} tone", tone);
            ResponseEntity<GeminiResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    GeminiResponse.class
            );

            if (response.getBody() != null &&
                    response.getBody().getCandidates() != null &&
                    !response.getBody().getCandidates().isEmpty()) {

                String generatedText = response.getBody()
                        .getCandidates()
                        .get(0)
                        .getContent()
                        .getParts()
                        .get(0)
                        .getText();

                log.info("Successfully generated reply");
                return generatedText;
            }

            throw new RuntimeException("Empty response from Gemini AI");

        } catch (HttpStatusCodeException httpError) {
            String responseBody = httpError.getResponseBodyAsString();
            String status = httpError.getStatusCode().toString();
            String reason = httpError.getStatusText();
            log.warn("Gemini API returned error status={} reason={} apiKeySuffix={} bodySnippet={}",
                    status,
                    reason,
                    maskKey(apiKey),
                    snippet(responseBody));

            boolean likelyAuthIssue = httpError.getStatusCode().is4xxClientError() &&
                    (responseBody != null && responseBody.toLowerCase().contains("api key"));

            String friendlyMessage = likelyAuthIssue
                    ? "Gemini API key may be invalid or expired. Please rotate GEMINI_API_KEY."
                    : "Gemini API call failed with status " + status + " (" + reason + ").";

            throw new RuntimeException("Failed to generate reply: " + friendlyMessage);

        } catch (Exception e) {
            log.error("Error generating reply with Gemini AI", e);
            throw new RuntimeException("Failed to generate reply: " + e.getMessage());
        }
    }

    private String buildPrompt(String emailBody, String tone, String sender, String subject) {
        String toneInstruction = getToneInstruction(tone);

        return String.format("""
            You are an expert email assistant. Your task is to generate a %s reply to the following email.
            
            %s
            
            Original Email:
            From: %s
            Subject: %s
            
            %s
            
            Generate ONLY the reply email body. Do not include greetings like "Dear [Name]" at the start.
            Do not include sign-offs like "Best regards" or signatures.
            Make it natural, contextual, and %s.
            """,
                tone,
                toneInstruction,
                sender != null ? sender : "Unknown",
                subject != null ? subject : "No Subject",
                emailBody,
                tone
        );
    }

    private String getToneInstruction(String tone) {
        return switch (tone.toLowerCase()) {
            case "professional" ->
                    "Use formal, business-appropriate language. Be polite, respectful, and maintain professional boundaries.";
            case "friendly" ->
                    "Use warm, conversational language. Be approachable and personable while maintaining professionalism.";
            case "concise" ->
                    "Be brief and to-the-point. Use short sentences and get straight to the message without unnecessary words.";
            default ->
                    "Use a balanced, professional tone.";
        };
    }

    private String snippet(String body) {
        if (body == null) return "null";
        String cleaned = body.replaceAll("\\s+", " ");
        return cleaned.substring(0, Math.min(cleaned.length(), 300));
    }

    private String maskKey(String key) {
        if (key == null || key.isBlank()) return "null";
        if (key.length() <= 4) return "***" + key;
        return "***" + key.substring(key.length() - 4);
    }
}