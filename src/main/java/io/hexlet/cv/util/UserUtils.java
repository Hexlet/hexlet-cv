package io.hexlet.cv.util;

import io.hexlet.cv.model.User;
import io.hexlet.cv.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserUtils {
    @Autowired
    private UserRepository userRepository;

// на потом чтобы работать с залогиненым юзером

    public Optional<User> getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return Optional.empty();
        }
        var principal = auth.getPrincipal();
        if (principal instanceof String s && "anonymousUser".equals(s)) {
            return Optional.empty();
        }
        var email = auth.getName();
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }
        return userRepository.findByEmail(email);
    }

    public Optional<Long> currentUserId() {
        return getCurrentUser().map(User::getId);
    }

    //  с 401 если не авторизован ---
    public Long requireCurrentUserId() {
        return currentUserId().orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to view this page")
        );
    }

    public boolean isAuthor(long userId) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return false;

        var principal = auth.getPrincipal();
        if (principal instanceof String s && "anonymousUser".equals(s)) return false;

        var currentEmail = auth.getName();
        if (currentEmail == null) return false;

        return userRepository.findById(userId)
                .map(User::getEmail)
                .map(currentEmail::equals)
                .orElse(false);
    }
}
