package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.config.CookieProperties;
import io.hexlet.cv.dto.user.LoginRequestDTO;
import io.hexlet.cv.dto.user.LoginResponseDTO;
import io.hexlet.cv.security.TokenCookieService;
import io.hexlet.cv.security.TokenService;
import io.hexlet.cv.service.LoginService;
import io.hexlet.cv.util.JWTUtils;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class LoginController {
    private final Inertia inertia;

    private LoginService loginService;

    private final TokenService tokenService;
    private final TokenCookieService tokenCookieService;

    @GetMapping("/{locale}/users/sign_in")
    public ResponseEntity<?> signInForm(@PathVariable String locale) {
        return inertia.render("Users/SignIn", Map.of("locale", locale));
    }

    @PostMapping(path = "/{locale}/users/sign_in")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginDTO,
                                          @PathVariable String locale) {
        LoginResponseDTO result = loginService.login(loginDTO);

        var tokens = tokenService.authenticateAndGenerate(loginDTO.getEmail(), loginDTO.getPassword());

        ResponseCookie accessCookie = tokenCookieService.buildAccessCookie(tokens.access());
        ResponseCookie refreshCookie = tokenCookieService.buildRefreshCookie(tokens.refresh());

        ResponseEntity<?> response = inertia.render("Users/Dashboard",
                Map.of("user", result, "locale", locale));
        return ResponseEntity.status(HttpStatus.OK)
                .headers(response.getHeaders())
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(response.getBody());
    }
}
