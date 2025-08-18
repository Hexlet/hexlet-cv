package io.hexlet.cv.controller;

import io.hexlet.cv.dto.user.UserPasswordDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/password")
    public ResponseEntity<String> getChangeAccountPassword() {
        return ResponseEntity.ok("passwordChange");
    }

    @PostMapping("/password")
    public ResponseEntity<String> ChangeAccountPassword(@Valid @RequestBody UserPasswordDto userPasswordDto) {
        return ResponseEntity.ok("profile");
    }
}
