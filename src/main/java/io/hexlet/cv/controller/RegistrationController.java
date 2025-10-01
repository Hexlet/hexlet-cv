package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.user.auth.RegistrationRequestDTO;
import io.hexlet.cv.security.AuthResponseService;
import io.hexlet.cv.security.TokenService;
import io.hexlet.cv.service.FlashPropsService;
import io.hexlet.cv.service.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@AllArgsConstructor
public class RegistrationController {

    private final Inertia inertia;
    private final RegistrationService userService;
    private final TokenService tokenService;
    private final AuthResponseService authResponseService;
    private final FlashPropsService flashPropsService;

    @GetMapping("/{locale}/users/sign_up")
    public ResponseEntity<?> signUpForm(@PathVariable String locale,
                                        HttpServletRequest request) {

        var props = flashPropsService.buildProps(locale, request);

        return inertia.render("Users/SignUp", props);
    }

    @PostMapping(path = "/{locale}/users")
    public Object registration(@Valid @RequestBody RegistrationRequestDTO inputDTO,
                                          @PathVariable String locale,
                                          HttpServletResponse response) {

        userService.registration(inputDTO);

        var tokens = tokenService.authenticateAndGenerate(
                inputDTO.getEmail(),
                inputDTO.getPassword()
        );

        return authResponseService.success(locale, tokens, response);
    }
}
