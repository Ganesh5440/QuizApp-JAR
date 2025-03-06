package com.example.quizapp;

import com.example.quizapp.service.GeminiService;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
public class MainController {
    private final GeminiService geminiService;
    private final RestTemplate restTemplate;

    public MainController(GeminiService geminiService) {
        this.geminiService = geminiService;
        this.restTemplate = new RestTemplate(); // Initialize RestTemplate
    }

    @GetMapping(value = "/index")
    public String getIndex() {
        return "index";
    }

    @GetMapping(value = "/generate-quiz")
    public String getGenerateQuiz() {
        return "generate-quiz";
    }

    @PostMapping("/generate-quiz")
    public String postGenerateQuiz(@RequestParam String topic, @RequestParam int count, Model model) {
        try {
            // Call API to generate quiz questions
            String apiUrl = "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash:generateContent?key=AIzaSyAhLdzC8ScNnEcRiZ6fs9SPwdfB9LDwnwE";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Construct API request payload
            String requestBody = String.format("""
        {
            "contents": [{
                "parts": [{
                    "text": "Generate %d multiple-choice questions on the topic: %s. 
                    Each question should have 4 options. 
                    Do not include questions asking 'What will be the output?'. 
                    Format:
                    Question: <question_text>
                    A) <option1>
                    B) <option2>
                    C) <option3>
                    D) <option4>"
                }]
            }]
        }
        """, count, topic);


            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();
                String generatedText = responseBody.toString(); // Modify parsing as needed
                String formattedQuiz = formatQuizQuestions(generatedText);

                // Pass formatted questions to JSP page
                model.addAttribute("quizQuestions", formattedQuiz);
                return "gemini_quiz"; // Redirect to quiz.jsp
            } else {
                throw new RuntimeException("Failed to generate quiz questions.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error: " + e.getMessage());
            return "error";
        }
    }

    // Function to format quiz questions
    private String formatQuizQuestions(String rawText) {
        String[] questions = rawText.split("Question:");
        StringBuilder formattedQuiz = new StringBuilder();

        for (String question : questions) {
            if (question.trim().isEmpty()) continue;

            String[] lines = question.split("\n");

            formattedQuiz.append("<div class='question'><strong>Question:</strong> ")
                    .append(lines[0].trim()).append("</div>");

            formattedQuiz.append("<div class='options'>");
            for (int i = 1; i < lines.length; i++) {
                if (lines[i].trim().isEmpty()) continue;
                formattedQuiz.append("<p>&#8226; ").append(lines[i].trim()).append("</p>");
            }
            formattedQuiz.append("</div>");
        }

        return formattedQuiz.toString();
    }

}
