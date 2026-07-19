package com.javainterviewcoach.question.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AnswerRequest(
        @NotBlank(message = "Cevap boş bırakılamaz")
        @Size(max = 3000, message = "Cevap en fazla 3000 karakter olabilir")
        String answer,
        String language
) {}
