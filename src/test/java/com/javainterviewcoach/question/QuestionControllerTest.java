package com.javainterviewcoach.question;

import com.javainterviewcoach.exception.GlobalExceptionHandler;
import com.javainterviewcoach.question.dto.AnswerResponse;
import com.javainterviewcoach.question.dto.QuestionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {QuestionController.class, GlobalExceptionHandler.class})
class QuestionControllerTest {
    @Autowired MockMvc mockMvc;
    @MockBean QuestionService service;
    @MockBean ReportedQuestionService reportedQuestionService;

    @Test
    void shouldGetRandomQuestion() throws Exception {
        when(service.getRandomQuestion(Category.OOP))
                .thenReturn(new QuestionResponse(4L, "OOP", "OOP", "Polymorphism nedir?"));

        mockMvc.perform(get("/api/questions/random").param("category", "OOP"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.text").value("Polymorphism nedir?"))
                .andExpect(jsonPath("$.sampleAnswer").doesNotExist());
    }

    @Test
    void shouldRejectBlankAnswer() throws Exception {
        mockMvc.perform(post("/api/questions/1/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"answer\":\"   \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Gönderilen bilgiler geçersiz"))
                .andExpect(jsonPath("$.validationErrors.answer").value("Cevap boş bırakılamaz"));
    }

    @Test
    void shouldCheckValidAnswer() throws Exception {
        when(service.checkAnswer(1L, "JVM bytecode çalıştırır"))
                .thenReturn(new AnswerResponse(1L, "Örnek", "Açıklama", null));

        mockMvc.perform(post("/api/questions/1/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"answer\":\"JVM bytecode çalıştırır\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sampleAnswer").value("Örnek"));
    }
}
