package io.hexlet.cv.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary//вместо реального EmailService
@Slf4j
public class FakeEmailService {

    public void sendResetEmail(String email, String clientUrl) {
        log.info("📧 [FAKE EMAIL] Password reset requested for: {}", email);
        log.info("📧 [FAKE EMAIL] Client URL: {}", clientUrl);
    }

    public void sendNewPasswordEmail(String email, String newPassword) {
        log.info("📧 [FAKE EMAIL] New password for {}: {}", email, newPassword);
    }
}
