package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.user.UserPasswordDto;
import io.hexlet.cv.service.PasswordService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class PasswordController {

    private final Inertia inertia;
    private final PasswordService passwordService;

    @GetMapping(path = "/password")
    public ResponseEntity<?> getChangeAccountPassword() {
        ResponseEntity<?> response = inertia.render("/users/password");
        return ResponseEntity.status(HttpStatus.OK)
                .headers(response.getHeaders())
                .body(response.getBody());
    }

    @PatchMapping(path = "/password")
    public ResponseEntity<?> ChangeAccountPassword(@Valid @RequestBody UserPasswordDto userPasswordDto) {
        passwordService.passwordChange(userPasswordDto);
        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .header(HttpHeaders.LOCATION, "/users")
                .build();
    }
}
