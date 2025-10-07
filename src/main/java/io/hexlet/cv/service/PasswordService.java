package io.hexlet.cv.service;

import com.fasterxml.jackson.core.JsonPointer;
import io.hexlet.cv.dto.user.UserPasswordDto;
import io.hexlet.cv.handler.exception.MatchingPasswordsException;
import io.hexlet.cv.handler.exception.WrongPasswordException;
import io.hexlet.cv.model.User;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.security.TokenCookieService;
import io.hexlet.cv.security.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PasswordService {

    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    
    public void passwordChange(UserPasswordDto userPasswordDto) {
        User user = userRepository.findByEmail(getCurrentUsername()).get();
        if (!encoder.matches(userPasswordDto.getOldPassword(), user.getEncryptedPassword())) {
            throw new WrongPasswordException("incorrect password entered");
        } else if (!userPasswordDto.getNewPassword().equals(
                userPasswordDto.getRepeatNewPassword())) {
            throw new MatchingPasswordsException("passwords must match");
        } else {
            user.setEncryptedPassword(encoder.encode(userPasswordDto.getNewPassword()));
            userRepository.save(user);
        }
    }

    public String extractRefreshToken(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (REFRESH_TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
