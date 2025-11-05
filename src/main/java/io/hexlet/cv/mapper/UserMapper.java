package io.hexlet.cv.mapper;

import io.hexlet.cv.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public Map<String, Object> toInertiaProps(User user) {
        return Map.of(
                "id", user.getId(),
                "email", user.getEmail(),
                "createdAt", user.getCreatedAt(),
                "updatedAt", user.getUpdatedAt(),
                "lastLoginAt", user.getLastLoginAt(),
                "role", user.getRole() != null ? user.getRole().name() : null,
                "enabled", user.isEnabled()
        );
    }

    public User toUser(String email, String password) {
        return User.builder()
                .email(email)
                .password(password)
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public List<Map<String, Object>> toInertiaPropsList(List<User> users) {
        return users.stream()
                .map(this::toInertiaProps)
                .collect(Collectors.toList());
    }
}
