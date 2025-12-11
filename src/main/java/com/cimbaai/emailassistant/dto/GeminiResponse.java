package com.cimbaai.emailassistant.dto;

import lombok.Data;

import java.util.List;

@Data
public class GeminiResponse {

    private List candidates;

    @Data
    public static class Candidate {
        private Content content;
    }

    @Data
    public static class Content {
        private List parts;
    }

    @Data
    public static class Part {
        private String text;
    }
}