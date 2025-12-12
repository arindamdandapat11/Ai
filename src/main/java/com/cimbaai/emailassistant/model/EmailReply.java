package com.cimbaai.emailassistant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "email_replies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String sender;

    @Column(length = 500)
    private String subject;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "Email body is required")
    private String body;

    @Column(length = 50, nullable = false)
    @NotBlank(message = "Tone is required")
    @Pattern(regexp = "^(professional|friendly|concise)$",
            message = "Tone must be professional, friendly, or concise")
    private String tone;

    @Column(name = "generated_reply", columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "Generated reply is required")
    private String generatedReply;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}