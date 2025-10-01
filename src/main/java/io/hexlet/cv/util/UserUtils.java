package io.hexlet.cv.util;

import io.hexlet.cv.model.User;
import io.hexlet.cv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {
    @Autowired
    private UserRepository userRepository;

// на потом чтобы работать с залогиненым юзером

    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        var email = authentication.getName();
        return userRepository.findByEmail(email).get();
    }

    public boolean isAuthor(long userId) {
        var userAuthorEmail = userRepository.findById(userId).get().getEmail();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return userAuthorEmail.equals(authentication.getName());
    }
}
