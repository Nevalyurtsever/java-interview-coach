package com.javainterviewcoach.question.dto;

public record ReportedQuestionResponse(String company, String role, String topic,
                                       String question, String sourceUrl) {}
