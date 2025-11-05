package io.hexlet.cv.reset;

import io.hexlet.cv.exception.InvalidTokenException;

import io.hexlet.cv.exception.WeakPasswordException;
import io.hexlet.cv.model.PasswordResetToken;
import io.hexlet.cv.model.User;
import io.hexlet.cv.repository.PasswordResetTokenRepository;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.service.PasswordResetService;
import io.hexlet.cv.validator.CommonPasswordValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PasswordResetServiceTest {

    @Mock
    private PasswordResetTokenRepository tokenRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CommonPasswordValidator commonPasswordValidator;

    @InjectMocks
    private PasswordResetService passwordResetService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("oldPassword")
                .firstName("Test")
                .lastName("User")
                .enabled(true)
                .build();
    }

    @Test
    void shouldResetPasswordWithValidToken() {
        // Given
        var validToken = PasswordResetToken.builder()
                .token("valid-token")
                .user(testUser)
                .expiryDate(LocalDateTime.now().plusHours(1))
                .build();

        when(tokenRepository.findByTokenWithUser("valid-token")).thenReturn(Optional.of(validToken));
        when(commonPasswordValidator.isCommonPassword("NewPass123!")).thenReturn(false);
        when(passwordEncoder.encode("NewPass123!")).thenReturn("encoded");

        // When
        String email = passwordResetService.resetPassword("valid-token", "NewPass123!");

        // Then
        assertThat(email).isEqualTo("test@example.com");
        verify(userRepository).save(testUser);
        verify(tokenRepository).delete(validToken);
    }

    @Test
    void shouldThrowForInvalidToken() {
        when(tokenRepository.findByTokenWithUser("invalid-token")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> passwordResetService.resetPassword("invalid-token", "NewPass123!"))
                .isInstanceOf(InvalidTokenException.class);
    }

    @Test
    void shouldThrowForExpiredToken() {
        var expiredToken = PasswordResetToken.builder()
                .token("expired-token")
                .user(testUser)
                .expiryDate(LocalDateTime.now().minusHours(1))
                .build();

        when(tokenRepository.findByTokenWithUser("expired-token")).thenReturn(Optional.of(expiredToken));

        assertThatThrownBy(() -> passwordResetService.resetPassword("expired-token", "NewPass123!"))
                .isInstanceOf(InvalidTokenException.class);

        verify(tokenRepository).delete(expiredToken);
    }

    @Test
    void shouldThrowForWeakPassword() {
        var validToken = PasswordResetToken.builder()
                .token("valid-token")
                .user(testUser)
                .expiryDate(LocalDateTime.now().plusHours(1))
                .build();

        when(tokenRepository.findByTokenWithUser("valid-token")).thenReturn(Optional.of(validToken));
        when(commonPasswordValidator.isCommonPassword("123456")).thenReturn(true);

        assertThatThrownBy(() -> passwordResetService.resetPassword("valid-token", "123456"))
                .isInstanceOf(WeakPasswordException.class);
    }

    @Test
    void shouldCreateResetToken() {
        when(tokenRepository.save(any(PasswordResetToken.class))).thenAnswer(inv -> {
            PasswordResetToken token = inv.getArgument(0);
            token.setId(1L);
            return token;
        });

        String token = passwordResetService.createPasswordResetToken(testUser);

        assertThat(token).isNotBlank();
        verify(tokenRepository).deleteByUserId(1L);
        verify(tokenRepository).save(any(PasswordResetToken.class));
    }
}
