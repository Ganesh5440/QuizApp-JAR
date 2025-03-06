package com.example.quizapp.subject;

import jakarta.persistence.*;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_name",  unique = true)
    private String subjectName;

    public Subject() {}

    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Subject(Long id, String subjectName) {
        this.id = id;
        this.subjectName = subjectName;
    }
}
