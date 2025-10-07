package io.hexlet.cv.controller;

import io.hexlet.cv.dto.user.UserPasswordDto;
import io.hexlet.cv.handler.exception.MatchingPasswordsException;
import io.hexlet.cv.handler.exception.WrongPasswordException;
import io.hexlet.cv.model.User;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.service.PasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestUser {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private PasswordService passwordService;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setEmail("test@gmail.com");
        user.setEncryptedPassword("Encrypted_old_password");
        user.setFirstName("firstName");
        user.setLastName("lastName");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@gmail.com");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testSuccessPasswordChange() {
        UserPasswordDto userPasswordDto = new UserPasswordDto(
                "Old_password", "New_password", "New_password");

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        when(encoder.matches("Old_password", "Encrypted_old_password"))
                .thenReturn(true);
        when(encoder.encode("New_password")).thenReturn("Encrypted_new_password");

        passwordService.passwordChange(userPasswordDto);

        assertEquals("Encrypted_new_password", user.getEncryptedPassword());
        verify(userRepository).save(user);
    }

    @Test
    public void testWrongOldPassword() {
        UserPasswordDto userPasswordDto = new UserPasswordDto(
                "Wrong_old_password", "New_password", "New_password");

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        when(encoder.matches("Wrong_old_password", "Encrypted_old_password"))
                .thenReturn(false);

        assertThrows(WrongPasswordException.class, () -> {
           passwordService.passwordChange(userPasswordDto);
        });

        verify(userRepository, never()).save(any());
    }

    @Test
    public void testWrongRepeatNewPassword() {
        UserPasswordDto userPasswordDto = new UserPasswordDto(
                "Old_password", "New_password", "New_wrong_password");

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        when(encoder.matches("Old_password", "Encrypted_old_password"))
                .thenReturn(true);

        assertThrows(MatchingPasswordsException.class, () -> {
            passwordService.passwordChange(userPasswordDto);
        });

        verify(userRepository, never()).save(any());
    }
}
