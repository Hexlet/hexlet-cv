package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.user.UserProfileDTO;
import io.hexlet.cv.service.FlashPropsService;
import io.hexlet.cv.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/{locale}/account/")
@RequiredArgsConstructor
public class UserController {
    private final Inertia inertia;
    private final UserService userService;

    @GetMapping("/profile/edit")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> getUserProfile(
            @PathVariable String locale,
            Principal principal) {

        String email = principal.getName();
        UserProfileDTO userProfileDTO = userService.getUserProfile(email);

        return inertia.render("", Map.of("userProfile", userProfileDTO)); //TODO добавить корректный компонент в inertia
    }
}
