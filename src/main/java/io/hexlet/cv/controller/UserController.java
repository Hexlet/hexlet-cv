package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.user.UserPasswordDto;
import io.hexlet.cv.service.UserService;
import jakarta.persistence.Access;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final Inertia inertia;
    private final UserService userService;

    @GetMapping(path = "/password")
    public ResponseEntity<?> getChangeAccountPassword() {
        ResponseEntity<?> response = inertia.render("/users/password");
        return ResponseEntity.status(HttpStatus.OK)
                .headers(response.getHeaders())
                .body(response.getBody());
    }

    @PostMapping(path = "/password")
    public ResponseEntity<?> ChangeAccountPassword(@Valid @RequestBody UserPasswordDto userPasswordDto) {
        userService.passwordChange(userPasswordDto);
        ResponseEntity<?> response = inertia.render("/users");
        return ResponseEntity.status(HttpStatus.OK)
                .headers(response.getHeaders())
                .body(response.getBody());
    }
}
