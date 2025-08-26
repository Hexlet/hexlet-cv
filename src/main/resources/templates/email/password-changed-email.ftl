<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Пароль изменен</title>
</head>
<body>
    <h2>Пароль успешно изменен</h2>
    <p>Здравствуйте, <span th:text="${username}"></span>!</p>
    <p>Ваш пароль был успешно изменен.</p>
    <p>Если вы не меняли пароль, пожалуйста, <a th:href="${supportUrl}">свяжитесь с технической поддержкой Hexlet CV</a>.</p>
    <p>С уважением,<br>Команда Hexlet CV</p>
</body>
</html>