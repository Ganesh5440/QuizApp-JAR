<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Generated Quiz</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            padding: 0;
            background-color: #f9f9f9;
        }
        .quiz-container {
            max-width: 800px;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        .question {
            margin-bottom: 20px;
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }
        .options {
            margin-left: 20px;
        }
    </style>
</head>
<body>

<div class="quiz-container">
    <h2>Generated Quiz</h2>

    <c:if test="${not empty quizQuestions}">
        <div>${quizQuestions}</div>
    </c:if>

    <c:if test="${empty quizQuestions}">
        <p>No questions generated. Try again!</p>
    </c:if>
</div>

</body>
</html>
