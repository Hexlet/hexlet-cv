<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Сброс пароля</title>
</head>
<body>
    <h2>Сброс пароля</h2>
    <p>Вы запросили сброс пароля. Перейдите по ссылке ниже для создания нового пароля:</p>
    <p><a th:href="${resetUrl}" style="background-color: #007bff; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;">Сбросить пароль</a></p>
    <p>Ссылка действительна <span th:text="${expiryHours}">24</span> часа.</p>
    <p>Если вы не запрашивали сброс пароля, просто проигнорируйте это письмо.</p>
    <p>С уважением,<br>Команда Hexlet CV</p>
</body>
</html>