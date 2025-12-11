package com.cimbaai.emailassistant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailReplyResponse {

    private Long id;
    private String sender;
    private String subject;
    private String body;
    private String tone;
    private String generatedReply;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    // Success response
    private boolean success;
    private String message;
}