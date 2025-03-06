package com.example.quizapp;

import java.util.List;

public class Question {
    private int id;
    private String questionText;
    private List<String> options;

    public Question(String questionText, List<String> options) {
        this.questionText = questionText;
        this.options = options;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", options=" + options +
                '}';
    }

    public int getId() {
        return id;
    }

    public Question(int id, String questionText, List<String> options) {
        this.id = id;
        this.questionText = questionText;
        this.options = options;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
