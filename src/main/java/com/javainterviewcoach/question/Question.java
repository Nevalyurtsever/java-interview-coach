package com.javainterviewcoach.question;

import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Category category;

    @Column(nullable = false, length = 500)
    private String text;

    @Column(nullable = false, length = 2000)
    private String sampleAnswer;

    @Column(nullable = false, length = 2000)
    private String explanation;

    protected Question() {}

    public Question(Category category, String text, String sampleAnswer, String explanation) {
        this.category = category;
        this.text = text;
        this.sampleAnswer = sampleAnswer;
        this.explanation = explanation;
    }

    public Long getId() { return id; }
    public Category getCategory() { return category; }
    public String getText() { return text; }
    public String getSampleAnswer() { return sampleAnswer; }
    public String getExplanation() { return explanation; }
}
