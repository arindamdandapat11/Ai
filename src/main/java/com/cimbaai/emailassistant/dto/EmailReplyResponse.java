package com.cimbaai.emailassistant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailReplyResponse {
    private Long id;
    private String generatedReply;
    private String tone;
    private String message;
    private boolean success;

    public EmailReplyResponse(Long id, String generatedReply, String tone) {
        this.id = id;
        this.generatedReply = generatedReply;
        this.tone = tone;
        this.success = true;
        this.message = "Email reply generated successfully";
    }

    public EmailReplyResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
