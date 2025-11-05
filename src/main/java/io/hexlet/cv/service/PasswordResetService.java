package io.hexlet.cv.service;

import io.hexlet.cv.exception.InvalidTokenException;
import io.hexlet.cv.exception.WeakPasswordException;
import io.hexlet.cv.model.PasswordResetToken;
import io.hexlet.cv.model.User;
import io.hexlet.cv.repository.PasswordResetTokenRepository;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.validator.CommonPasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CommonPasswordValidator commonPasswordValidator;

    public String resetPassword(String token, String newPassword) {

        PasswordResetToken resetToken = tokenRepository.findByTokenWithUser(token)
                .orElseThrow(() -> new InvalidTokenException("Invalid reset token"));

        if (resetToken.isExpired()) {
            tokenRepository.delete(resetToken);
            throw new InvalidTokenException("Reset token has expired");
        }

        User user = resetToken.getUser();
        if (!user.isEnabled()) {
            tokenRepository.delete(resetToken);
            throw new InvalidTokenException("User account is disabled");
        }

        if (commonPasswordValidator.isCommonPassword(newPassword)) {
            throw new WeakPasswordException("Password is too common");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        tokenRepository.delete(resetToken);

        return user.getEmail();
    }

    public String createPasswordResetToken(User user) {

        tokenRepository.deleteByUserId(user.getId());

        PasswordResetToken resetToken = PasswordResetToken.createForUser(user);
        tokenRepository.save(resetToken);

        return resetToken.getToken();
    }

    public boolean isTokenValid(String token) {
        return tokenRepository.findByTokenWithUser(token)
                .map(resetToken ->
                        resetToken.isValid() &&
                                resetToken.getUser().isEnabled()
                )
                .orElse(false);
    }

    public Optional<User> getUserByToken(String token) {
        return tokenRepository.findByTokenWithUser(token)
                .filter(PasswordResetToken::isValid)
                .filter(resetToken -> resetToken.getUser().isEnabled())
                .map(PasswordResetToken::getUser);
    }
}
