package com.javainterviewcoach.question;

import com.javainterviewcoach.exception.QuestionNotFoundException;
import com.javainterviewcoach.question.dto.AnswerResponse;
import com.javainterviewcoach.question.dto.QuestionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;
import com.javainterviewcoach.evaluation.OllamaEvaluationService;
import com.javainterviewcoach.evaluation.EvaluationResponse;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {
    @Mock QuestionRepository repository;
    @Mock RandomGenerator random;
    @Mock OllamaEvaluationService evaluationService;
    private QuestionService service;

    @BeforeEach
    void setUp() {
        service = new QuestionService(repository, random, evaluationService);
    }

    @Test
    void shouldReturnRandomQuestionWithoutAnswer() {
        Question first = question("Birinci soru");
        Question second = question("İkinci soru");
        when(repository.findByCategory(Category.JAVA_CORE)).thenReturn(List.of(first, second));
        when(random.nextInt(2)).thenReturn(1);

        QuestionResponse response = service.getRandomQuestion(Category.JAVA_CORE);

        assertThat(response.text()).isEqualTo("İkinci soru");
        assertThat(response.categoryName()).isEqualTo("Java Core");
        verify(repository).findByCategory(Category.JAVA_CORE);
    }

    @Test
    void shouldThrowWhenCategoryHasNoQuestion() {
        when(repository.findByCategory(Category.SQL)).thenReturn(List.of());

        assertThatThrownBy(() -> service.getRandomQuestion(Category.SQL))
                .isInstanceOf(QuestionNotFoundException.class)
                .hasMessageContaining("SQL");
    }

    @Test
    void shouldReturnAnswerForExistingQuestion() {
        Question question = question("Soru");
        when(repository.findById(7L)).thenReturn(Optional.of(question));
        when(evaluationService.evaluate(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new EvaluationResponse(70, "İyi", List.of(), List.of(), List.of(), "Geliştirilmiş"));

        AnswerResponse response = service.checkAnswer(7L, "Kendi cevabım");

        assertThat(response.sampleAnswer()).isEqualTo("Örnek cevap");
        assertThat(response.explanation()).isEqualTo("Açıklama");
    }

    private Question question(String text) {
        return new Question(Category.JAVA_CORE, text, "Örnek cevap", "Açıklama");
    }
}
