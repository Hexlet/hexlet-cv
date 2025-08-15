package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.registration.RegInputDTO;
import io.hexlet.cv.dto.registration.RegOutputDTO;
import io.hexlet.cv.exception.UserAlreadyExistsException;
import io.hexlet.cv.service.UserService;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class RegistrationController {

    private final Inertia inertia;
    private UserService userService;

    // чтобы не писал при 409 большую простыню а только коротко об ошибке
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
    }

    @PostMapping(path = "/registration")
    // @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registration(@Valid @RequestBody RegInputDTO inputDTO) {
        RegOutputDTO result = userService.registration(inputDTO);

        // отдать данные и отдать html страничку с ними через inertia
        ResponseEntity<?> response = inertia
                .render("users/RegistrationResult", Map.of("user", result));
        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(response.getHeaders())
                .body(response.getBody());
        // редирект на какую то страницу после регистрации через inertia
        // return inertia.redirect("/");
    }
}
