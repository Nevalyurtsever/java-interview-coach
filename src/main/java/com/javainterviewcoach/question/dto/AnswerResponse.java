package com.javainterviewcoach.question.dto;

import com.javainterviewcoach.evaluation.EvaluationResponse;

public record AnswerResponse(Long questionId, String sampleAnswer, String explanation,
                             EvaluationResponse evaluation) {}
