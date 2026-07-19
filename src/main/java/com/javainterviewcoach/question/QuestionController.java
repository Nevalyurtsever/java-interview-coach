package com.javainterviewcoach.question;

import com.javainterviewcoach.question.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionController {
    private final QuestionService service;
    private final ReportedQuestionService reportedQuestionService;

    public QuestionController(QuestionService service, ReportedQuestionService reportedQuestionService) {
        this.service = service;
        this.reportedQuestionService = reportedQuestionService;
    }

    @GetMapping("/categories")
    public List<CategoryResponse> getCategories() {
        return service.getCategories();
    }

    @GetMapping("/reported-questions")
    public List<ReportedQuestionResponse> getReportedQuestions() {
        return reportedQuestionService.getReportedQuestions();
    }

    @GetMapping("/questions/random")
    public QuestionResponse getRandomQuestion(@RequestParam Category category,
                                              @RequestParam(defaultValue = "tr") String lang) {
        return service.getRandomQuestion(category, lang);
    }

    @PostMapping("/questions/{id}/check")
    public ResponseEntity<AnswerResponse> checkAnswer(@PathVariable Long id,
                                                       @Valid @RequestBody AnswerRequest request) {
        return ResponseEntity.ok(service.checkAnswer(id, request.answer(), request.language()));
    }
}
