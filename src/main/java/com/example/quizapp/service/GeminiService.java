package com.example.quizapp.service;

import com.example.quizapp.Qns;
import com.example.quizapp.QnsRepository;
import com.example.quizapp.SubjectRepository;
import com.example.quizapp.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash:generateContent";
 // ✅ Read API key from application.properties
    private String apiKey="AIzaSyAhLdzC8ScNnEcRiZ6fs9SPwdfB9LDwnwE";

    public GeminiService( SubjectRepository subjectRepository, QnsRepository qnsRepository, RestTemplate restTemplate) {
        this.subjectRepository = subjectRepository;
        this.qnsRepository = qnsRepository;
        this.restTemplate = restTemplate;
    }

    private SubjectRepository subjectRepository;
    private QnsRepository qnsRepository;
    private RestTemplate restTemplate;// ✅ Inject Subject Repository

    public List<Qns> generateQuestions(String subjectName, int countOfQuestions) {
        List<Qns> questions = new ArrayList<>();

            if(!subjectRepository.existsBySubjectName(subjectName.trim())){
                subjectRepository.save(new Subject(subjectName.trim()));
            }


        // ✅ Ensure the subject exists in the database
        Subject subject = subjectRepository.findBySubjectName(subjectName.trim()).get();
        if (subject == null) {
            subject = new Subject(subjectName);
            subjectRepository.save(subject);  // ✅ Save the new subject
        }

        String prompt = "Generate " + countOfQuestions + " multiple-choice questions on the topic: " + subjectName +
                ". Each question should have 4 options. Format: \n" +
                "Question: <question_text>\n" +
                "A) <option1>\nB) <option2>\nC) <option3>\nD) <option4>";

        String requestBody = "{ \"contents\": [{ \"parts\": [{ \"text\": \"" + prompt + "\" }]}]}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            String requestUrl = GEMINI_API_URL + "?key=" + apiKey;
            ResponseEntity<Map> response = restTemplate.exchange(requestUrl, HttpMethod.POST, requestEntity, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
                if (candidates != null && !candidates.isEmpty()) {
                    Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
                    List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                    System.out.println("API Response: " + response.getBody());

                    if (parts != null && !parts.isEmpty()) {
                        String generatedText = (String) parts.get(0).get("text");

                        String[] questionBlocks = generatedText.split("\n\n");
                        for (String block : questionBlocks) {
                            String[] lines = block.split("\n");
                            if (lines.length >= 5) {
                                String questionText = lines[0].replace("Question: ", "").trim();
                                List<String> options = new ArrayList<>();
                                for (int i = 1; i <= 4; i++) {
                                    options.add(lines[i].substring(3).trim());
                                }

                                // ✅ Ensure we associate the question with the subject
                                Qns qns = new Qns(questionText, options, subject);
                                questions.add(qns);

                                qnsRepository.save(qns);  // ✅ Save to database
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }
}
