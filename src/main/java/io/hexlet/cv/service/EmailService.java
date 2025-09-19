package io.hexlet.cv.service;

import freemarker.template.Configuration;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import freemarker.template.Template;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.ui.freemarker.FreeMarkerTemplateUtils.processTemplateIntoString;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final Configuration freemarkerConfig;

    @Async
    public void sendHtmlEmail(String to, String subject, String emailBody) throws MessagingException {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(emailBody, true);
        mailSender.send(message);
    }

    // Отправка приветственного письма после регистрации
    public void sendWelcomeEmail(String to, String username) throws MessagingException {
        Map<String, Object> model = new HashMap<>();
        model.put("username", username);

        String subject = "Добро пожаловать!";
        String htmlBody = processTemplate("welcome-email.ftl", model);

        sendHtmlEmail(to, subject, htmlBody);
    }

    // Отправка письма для верификации email
    public void sendVerificationEmail(String to, String token) throws MessagingException {
        Map<String, Object> model = new HashMap<>();
        model.put("verificationUrl", "/verify-email?token=" + token);

        String subject = "Подтверждение адреса электронной почты";
        String htmlBody = processTemplate("verification-email.ftl", model);

        sendHtmlEmail(to, subject, htmlBody);
    }

    // Отправка письма для сброса пароля
    public void sendPasswordResetEmail(String to, String token) throws MessagingException {
        Map<String, Object> model = new HashMap<>();
        model.put("resetUrl", "/password-reset?token=" + token);
        model.put("expiryHours", 24);

        String subject = "Сброс пароля";
        String htmlBody = processTemplate("password-reset-email.ftl", model);

        sendHtmlEmail(to, subject, htmlBody);
    }

    // Уведомление о смене пароля
    public void sendPasswordChangedEmail(String to, String username) throws MessagingException {
        Map<String, Object> model = new HashMap<>();
        model.put("username", username);
        model.put("supportUrl", "/password-changed");

        String subject = "Пароль успешно изменен";
        String htmlBody = processTemplate("password-changed-email.ftl", model);

        sendHtmlEmail(to, subject, htmlBody);
    }

    // Уведомление о входе с другого устройства
    public void sendNewDeviceLoginEmail(String to, String username, String ipAddress, String userAgent, LocalDateTime loginTime) throws MessagingException {
        Map<String, Object> model = new HashMap<>();
        model.put("username", username);
        model.put("ipAddress", ipAddress);
        model.put("userAgent", userAgent);
        model.put("loginTime", loginTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        model.put("securityUrl", "/new-device-login");

        String subject = "Новый вход в ваш аккаунт";
        String htmlBody = processTemplate("new-device-email.ftl", model);

        sendHtmlEmail(to, subject, htmlBody);
    }

    private String processTemplate(String templateName, Map<String, Object> model) {
        try {
            Template template = freemarkerConfig.getTemplate(templateName);
            return processTemplateIntoString(template, model);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка обработки шаблона email: " + templateName, e);
        }
    }
}
