package io.hexlet.cv.service;

import io.hexlet.cv.exception.EmailSendingException;
import lombok.extern.slf4j.Slf4j;
import io.hexlet.cv.exception.UserNotFoundException;
import io.hexlet.cv.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import io.hexlet.cv.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final PasswordResetTokenService passwordResetTokenService;
    private final UserRepository userRepository;

    @Autowired(required = false)
    private final Environment environment;

    public void sendResetEmail(String email, String clientUrl) {
        if (!userRepository.existsByEmail(email)) {
            log.info("Password reset attempt for non-existent email: {}", email);
            return;
        }

        if (isDevProfile()) {
            String resetToken = passwordResetTokenService.createPasswordResetToken(
                    userRepository.findByEmail(email).orElseThrow()
            );
            String resetLink = clientUrl + "?token=" + resetToken;

            log.info("🎯 DEV MODE: Password reset link for {}: {}", email, resetLink);
            return;
        }

        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            String resetToken = passwordResetTokenService.createPasswordResetToken(user);
            String resetLink = clientUrl + "?token=" + resetToken;

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText(createResetEmailText(resetLink));

            mailSender.send(message);
            log.info("Password reset email sent to: {}", email);

        } catch (Exception e) {
            log.error("Failed to send reset email to: {}", email, e);
            throw new EmailSendingException("Failed to send reset email");
        }
    }


    public void sendNewPasswordEmail(String email, String newPassword) {
        if (isDevProfile()) {
            log.info("🎯 DEV MODE: New password for {}: {}", email, newPassword);
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Your New Password");
            message.setText(createNewPasswordEmailText(newPassword));

            mailSender.send(message);
            log.info("New password email sent to: {}", email);

        } catch (Exception e) {
            log.error("Failed to send new password email to: {}", email, e);
            throw new EmailSendingException("Failed to send new password email");
        }
    }

    private boolean isDevProfile() {
        try {
            return environment != null &&
                    (environment.matchesProfiles("dev") ||
                            environment.matchesProfiles("test"));
        } catch (Exception e) {
            // Fallback проверка
            String activeProfile = System.getProperty("spring.profiles.active", "");
            return activeProfile.contains("dev") || activeProfile.contains("test");
        }
    }

    private String createResetEmailText(String resetLink) {
        return """
            Hello,
            
            You requested a password reset. Click the link below to reset your password:
            
            %s
            
            This link will expire in 1 hour.
            
            If you didn't request this, please ignore this email.
            
            Best regards,
            Support Team
            """.formatted(resetLink);
    }

    private String createNewPasswordEmailText(String newPassword) {
        return """
            Hello,
            
            Your password has been successfully reset.
            
            Your new password is: %s
            
            Please login and change your password immediately.
            
            Best regards,
            Support Team
            """.formatted(newPassword);
    }
}

