package com.cimbaai.emailassistant.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailReplyRequest {

    @NotBlank(message = "Email content cannot be empty")
    private String emailContent;

    @NotBlank(message = "Tone must be specified")
    private String tone;

    private String sender;
    private String subject;

    public String getBody() {
    }
}