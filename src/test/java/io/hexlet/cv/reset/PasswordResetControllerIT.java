package io.hexlet.cv.reset;

import io.hexlet.cv.exception.InvalidTokenException;
import io.hexlet.cv.model.User;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.repository.PasswordResetTokenRepository;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.service.EmailService;
import io.hexlet.cv.service.PasswordResetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class PasswordResetControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private JavaMailSender javaMailSender;

    @MockitoBean
    private EmailService emailService;

    private User testUser;
    private final String TEST_EMAIL = "test@example.com";
    private final String TEST_PASSWORD = "Password123!";

    @BeforeEach
    void setUp() {
        tokenRepository.deleteAll();
        userRepository.deleteAll();

        testUser = User.builder()
                .email(TEST_EMAIL)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .firstName("Test")
                .lastName("User")
                .role(RoleType.GUEST)
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(testUser);

        doNothing().when(emailService).sendResetEmail(anyString(), anyString());
    }

    private MockHttpServletRequestBuilder postWithCsrf(String url) {
        return post(url).with(csrf());
    }

    @Test
    void shouldCreateTokenForExistingEmail() {
        String token = passwordResetService.createPasswordResetToken(testUser);

        assertThat(tokenRepository.count()).isEqualTo(1);
        assertThat(tokenRepository.findByToken(token)).isPresent();
    }


    @Test
    void shouldResetPasswordWithValidToken() {
        String token = passwordResetService.createPasswordResetToken(testUser);
        String newPassword = "NewPassword123!";

        String userEmail = passwordResetService.resetPassword(token, newPassword);

        assertThat(userEmail).isEqualTo(TEST_EMAIL);
        User updatedUser = userRepository.findByEmail(TEST_EMAIL).orElseThrow();
        assertThat(passwordEncoder.matches(newPassword, updatedUser.getPassword())).isTrue();

        assertThat(tokenRepository.findByToken(token)).isEmpty();
    }


    @Test
    void shouldReturnErrorForExpiredToken() {
        String token = passwordResetService.createPasswordResetToken(testUser);
        var resetToken = tokenRepository.findByToken(token).orElseThrow();
        resetToken.setExpiryDate(LocalDateTime.now().minusHours(2));
        tokenRepository.save(resetToken);

        assertThatThrownBy(() ->
                passwordResetService.resetPassword(token, "NewPassword123!")
        ).isInstanceOf(InvalidTokenException.class);

        assertThat(tokenRepository.findByToken(token)).isEmpty();
    }


    @Test
    void shouldHandleNonExistentEmailGracefully() throws Exception {
        String nonExistentEmail = "nonexistent@example.com";

        mockMvc.perform(postWithCsrf("/auth/password/forgot")
                        .param("email", nonExistentEmail))
                .andExpect(status().isOk());

        assertThat(tokenRepository.count()).isZero();
    }

    @Test
    void shouldPreventTokenReuse() {
        String token = passwordResetService.createPasswordResetToken(testUser);
        String newPassword = "NewPassword123!";

        passwordResetService.resetPassword(token, newPassword);

        assertThatThrownBy(() ->
                passwordResetService.resetPassword(token, "SecondAttempt123!")
        ).isInstanceOf(InvalidTokenException.class)
                .hasMessageContaining("Invalid reset token");
    }

    @Test
    void shouldReturnErrorForInvalidToken() {
        assertThatThrownBy(() ->
                passwordResetService.resetPassword("invalid-token-123", "NewPassword123!")
        ).isInstanceOf(InvalidTokenException.class)
                .hasMessageContaining("Invalid reset token");
    }
}
