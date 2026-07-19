package com.javainterviewcoach;

import com.javainterviewcoach.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JavaInterviewCoachApplicationTests {
    @Autowired QuestionRepository repository;

    @Test
    void contextLoadsAndSeedsQuestions() {
        assertThat(repository.count()).isGreaterThanOrEqualTo(25);
    }
}
