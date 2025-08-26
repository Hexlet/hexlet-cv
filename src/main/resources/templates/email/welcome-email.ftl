<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Добро пожаловать!</title>
</head>
<body>
    <h2>Добро пожаловать, <span th:text="${username}"></span>!</h2>
    <p>Спасибо за регистрацию на Hexlet CV.</p>
    <p>Теперь вы можете <a th:href="${loginUrl}">войти в свой аккаунт</a>.</p>
    <p>С уважением,<br>Команда Hexlet CV</p>
</body>
</html>