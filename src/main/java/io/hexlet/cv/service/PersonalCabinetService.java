package io.hexlet.cv.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import io.hexlet.cv.dto.user.MenuItemDto;
import io.hexlet.cv.dto.user.UserCabinetDto;
import io.hexlet.cv.dto.user.UserDto;
import io.hexlet.cv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PersonalCabinetService {
    private final UserRepository userRepository;

    public UserCabinetDto getCabinetData(Authentication auth) {
        var email = auth.getName();
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // меню пока одинаковое для всех
        var menu = List.of(
                new MenuItemDto("Profile", "/profile", "profile-icon"),
                new MenuItemDto("Settings", "/settings", "settings-icon"),
                new MenuItemDto("Notifications", "/notifications", "bell-icon")
        );

        var userDto = new UserDto(
                user.getFirstName(),
                user.getLastName(),
                user.getRole().name(),
                user.getAvatarUrl()
        );

        return new UserCabinetDto(
                userDto,
                menu,
                Map.of("email_notifications", true, "push_notifications", false)
        );
    }
}
