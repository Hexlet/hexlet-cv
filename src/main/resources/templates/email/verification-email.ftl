<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Подтверждение email</title>
</head>
<body>
    <h2>Подтверждение адреса электронной почты</h2>
    <p>Пожалуйста, подтвердите ваш email, перейдя по ссылке ниже:</p>
    <p><a th:href="${verificationUrl}" style="background-color: #007bff; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;">Подтвердить email</a></p>
    <p>Если вы не регистрировались, просто проигнорируйте это письмо.</p>
    <p>С уважением,<br>Команда Hexlet CV</p>
</body>
</html>