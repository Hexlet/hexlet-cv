package io.hexlet.cv.service;

import io.hexlet.cv.exception.UserAlreadyExistsException;
import io.hexlet.cv.exception.UserNotFoundException;
import io.hexlet.cv.exception.WeakPasswordException;
import io.hexlet.cv.model.User;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.validator.CommonPasswordValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CommonPasswordValidator commonPasswordValidator;

    public User register(String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User already exists");
        }

        if (commonPasswordValidator.isCommonPassword(password)) {
            throw new WeakPasswordException("Password is too common");
        }

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(RoleType.CANDIDATE)
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    public void updateLastLogin(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.updateLastLogin();
        userRepository.save(user);
    }

    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }
}

