package com.cimbaai.emailassistant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailReplyRequest {

    private String sender;

    private String subject;

    @NotBlank(message = "Email body cannot be empty")
    private String body;

    @NotBlank(message = "Tone is required")
    @Pattern(regexp = "^(professional|friendly|concise)$",
            message = "Tone must be professional, friendly, or concise")
    private String tone;
}