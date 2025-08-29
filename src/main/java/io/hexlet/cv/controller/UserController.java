package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.user.UserPasswordDto;
import io.hexlet.cv.handler.exception.MatchingPasswordsException;
import io.hexlet.cv.handler.exception.WrongPasswordException;
import io.hexlet.cv.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final Inertia inertia;
    private final UserService userService;

    @GetMapping(path = "/password")
    public ResponseEntity<?> getChangeAccountPassword(@Valid @RequestBody UserPasswordDto userPasswordDto) {
        ResponseEntity<?> response = inertia.render(
                "/users/password", Map.of("password", userPasswordDto));
        return ResponseEntity.status(HttpStatus.OK)
                .headers(response.getHeaders())
                .body(response.getBody());
    }

    @PatchMapping(path = "/password")
    public ResponseEntity<?> ChangeAccountPassword(@Valid @RequestBody UserPasswordDto userPasswordDto) {
        try {
            userService.passwordChange(userPasswordDto);
        } catch (MatchingPasswordsException | WrongPasswordException e) {
            ResponseEntity<?> response = inertia.render("/users/password",
                    Map.of("password", userPasswordDto, "exception", e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .headers(response.getHeaders())
                    .body(response.getBody());
        }
        ResponseEntity<?> response = inertia.render("/users");
        return ResponseEntity.status(HttpStatus.OK)
                .headers(response.getHeaders())
                .body(response.getBody());
    }
}
