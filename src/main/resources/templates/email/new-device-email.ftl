<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Новый вход в аккаунт</title>
</head>
<body>
    <h2>Новый вход в ваш аккаунт</h2>
    <p>Здравствуйте, <span th:text="${username}"></span>!</p>
    <p>Обнаружен новый вход в ваш аккаунт:</p>
    <ul>
        <li><strong>IP адрес:</strong> <span th:text="${ipAddress}"></span></li>
        <li><strong>Устройство:</strong> <span th:text="${userAgent}"></span></li>
        <li><strong>Время:</strong> <span th:text="${loginTime}"></span></li>
    </ul>
    <p>Если это были не вы, пожалуйста, <a th:href="${securityUrl}">проверьте безопасность аккаунта</a>.</p>
    <p>С уважением,<br>Команда Hexlet CV</p>
</body>
</html>