<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Generate Quiz</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center">Generate Quiz</h2>
    <form action="generate-quiz" method="POST" class="mt-4">
        <div class="mb-3">
            <label for="subject" class="form-label">Subject Name</label>
            <input type="text" class="form-control" id="subject" name="topic" required>
        </div>
        <div class="mb-3">
            <label for="numQuestions" class="form-label">Number of Questions</label>
            <input type="number" class="form-control" id="numQuestions" name="count" required min="1">
        </div>
        <button type="submit" class="btn btn-success">Create Quiz</button>
    </form>
</div>
</body>
</html>
