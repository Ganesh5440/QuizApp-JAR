package com.example.quizapp;

import com.example.quizapp.subject.Subject;
import jakarta.persistence.*;
import java.util.List;

@Entity

public class Qns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)  // ✅ This ensures questionText cannot be null
    private String questionText;

    @ElementCollection
    private List<String> options;
    @ManyToOne  // ✅ Define relationship with Subject entity
    @JoinColumn(name = "subject_id", nullable = false)  // ✅ Ensure it is NOT NULL
    private Subject subject;

    public Qns(String questionText, List<String> options, Subject subject) {
        this.questionText = questionText;
        this.options = options;
        this.subject = subject;
    }
    public Qns() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }


// Getters and Setters
}
