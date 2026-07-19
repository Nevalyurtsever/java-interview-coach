package com.javainterviewcoach.question;

import com.javainterviewcoach.exception.QuestionNotFoundException;
import com.javainterviewcoach.evaluation.OllamaEvaluationService;
import com.javainterviewcoach.question.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

@Service
@Transactional(readOnly = true)
public class QuestionService {
    private final QuestionRepository repository;
    private final RandomGenerator random;
    private final OllamaEvaluationService evaluationService;

    @Autowired
    public QuestionService(QuestionRepository repository, OllamaEvaluationService evaluationService) {
        this(repository, new Random(), evaluationService);
    }

    QuestionService(QuestionRepository repository, RandomGenerator random, OllamaEvaluationService evaluationService) {
        this.repository = repository;
        this.random = random;
        this.evaluationService = evaluationService;
    }

    public List<CategoryResponse> getCategories() {
        return Arrays.stream(Category.values())
                .map(category -> new CategoryResponse(category.name(), category.getDisplayName()))
                .toList();
    }

    public QuestionResponse getRandomQuestion(Category category) {
        return getRandomQuestion(category, "tr");
    }

    public QuestionResponse getRandomQuestion(Category category, String language) {
        List<Question> questions = repository.findByCategory(category);
        if (questions.isEmpty()) {
            throw new QuestionNotFoundException(category.getDisplayName() + " kategorisinde soru bulunamadı");
        }
        Question question = questions.get(random.nextInt(questions.size()));
        var content = QuestionLocalization.get(question, language);
        return new QuestionResponse(question.getId(), category.name(), category.getDisplayName(), content.question());
    }

    public AnswerResponse checkAnswer(Long questionId, String userAnswer) {
        return checkAnswer(questionId, userAnswer, "tr");
    }

    public AnswerResponse checkAnswer(Long questionId, String userAnswer, String language) {
        Question question = repository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("Soru bulunamadı: " + questionId));
        var content = QuestionLocalization.get(question, language);
        var evaluation = evaluationService.evaluate(
                content.question(), content.answer(), content.explanation(), userAnswer, language);
        return new AnswerResponse(question.getId(), content.answer(), content.explanation(), evaluation);
    }
}
