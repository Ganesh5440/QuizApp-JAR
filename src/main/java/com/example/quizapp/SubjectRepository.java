package com.example.quizapp;

import com.example.quizapp.subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject,Long> {
    Optional<Subject> findBySubjectName(String subjectName);
   Boolean existsBySubjectName(String subjectName);

}
