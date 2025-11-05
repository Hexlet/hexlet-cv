package io.hexlet.cv.reset;

import io.hexlet.cv.exception.UserAlreadyExistsException;
import io.hexlet.cv.exception.WeakPasswordException;
import io.hexlet.cv.model.User;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.service.AuthService;
import io.hexlet.cv.validator.CommonPasswordValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CommonPasswordValidator commonPasswordValidator;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldRegisterNewUser() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(commonPasswordValidator.isCommonPassword("Password123!")).thenReturn(false);
        when(passwordEncoder.encode("Password123!")).thenReturn("encoded");

        authService.register("test@example.com", "Password123!");

        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowWhenUserExists() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        assertThatThrownBy(() -> authService.register("test@example.com", "Password123!"))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    @Test
    void shouldThrowForWeakPassword() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(commonPasswordValidator.isCommonPassword("123456")).thenReturn(true);

        assertThatThrownBy(() -> authService.register("test@example.com", "123456"))
                .isInstanceOf(WeakPasswordException.class);
    }
}
