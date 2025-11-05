package io.hexlet.cv.component;

import io.hexlet.cv.model.User;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
@Profile("dev")
public class DataInitializer {

    // этот класс для разработки и тестирования
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        createUserIfNotExists("guest@example.com", "password", RoleType.GUEST, "Guest", "User");
        createUserIfNotExists("admin@example.com", "password", RoleType.ADMIN, "Admin", "User");
    }

    private void createUserIfNotExists(String email, String password, RoleType role,
                                       String firstName, String lastName) {
        if (userRepository.findByEmail(email).isEmpty()) {
            User user = User.builder()
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .firstName(firstName)
                    .lastName(lastName)
                    .role(role)
                    .enabled(true)
                    .createdAt(LocalDateTime.now())
                    .build();
            userRepository.save(user);
            System.out.println("✅ Created user: " + email + " / " + password);
        }
    }
}
