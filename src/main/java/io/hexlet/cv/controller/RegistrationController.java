package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.registration.RegInputDTO;
import io.hexlet.cv.dto.registration.RegOutputDTO;
import io.hexlet.cv.service.UserService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class RegistrationController {

    private final Inertia inertia;
    private UserService userService;

    @PostMapping(path = "/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registration(@Valid @RequestBody RegInputDTO inputDTO) {
        RegOutputDTO result = userService.registration(inputDTO);

// отдать данные и отдать html страничку с ними --------------------------
      //  return inertia.render("users/RegistrationResult", Map.of(
      //          "user", result
      //  ));

// редирект на какую то страницу после регистрации ----------------------
        return inertia.redirect("/");
    }

}
