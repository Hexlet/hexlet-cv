package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.user.LoginRequestDTO;
import io.hexlet.cv.security.AuthResponseService;
import io.hexlet.cv.security.TokenService;
import io.hexlet.cv.service.FlashPropsService;
import io.hexlet.cv.service.LoginService;
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
public class LoginController {

    private final Inertia inertia;
    private final LoginService loginService;
    private final TokenService tokenService;
    private final AuthResponseService authResponseService;
    private final FlashPropsService flashPropsService;

    @GetMapping("/{locale}/users/sign_in")
    public ResponseEntity<?> signInForm(@PathVariable String locale,
                                        HttpServletRequest request) {

        var props = flashPropsService.buildProps(locale, request);

        return inertia.render("Users/SignIn", props);
    }

    @PostMapping(path = "/{locale}/users/sign_in")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginDTO,
                                   @PathVariable String locale,
                                   HttpServletResponse response) {

        loginService.login(loginDTO);
        var tokens = tokenService.authenticateAndGenerate(
                loginDTO.getEmail(),
                loginDTO.getPassword()
        );
        return authResponseService.success(locale, tokens, response);
    }
}
