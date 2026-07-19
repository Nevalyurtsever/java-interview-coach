package com.javainterviewcoach.evaluation;

import java.util.List;

public record EvaluationResponse(
        int score,
        String summary,
        List<String> correctPoints,
        List<String> incorrectPoints,
        List<String> missingPoints,
        String improvedAnswer
) {}
