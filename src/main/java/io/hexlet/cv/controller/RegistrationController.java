package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.config.CookieProperties;
import io.hexlet.cv.dto.user.RegistrationRequestDTO;
import io.hexlet.cv.dto.user.RegistrationResponseDTO;
import io.hexlet.cv.security.TokenCookieService;
import io.hexlet.cv.security.TokenService;
import io.hexlet.cv.service.RegistrationService;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class RegistrationController {

    private final Inertia inertia;

    private RegistrationService userService;

    private final TokenService tokenService;
    private final TokenCookieService tokenCookieService;

    @Autowired
    private CookieProperties cookieProperties;

   // @Autowired
  //  private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/{locale}/users/sign_up")
    public ResponseEntity<?> signUpForm(@PathVariable String locale) {
        return inertia.render("Users/SignUp", Map.of("locale", locale));
    }

    @PostMapping(path = "/{locale}/users")
    public ResponseEntity<?> registration(@Valid @RequestBody RegistrationRequestDTO inputDTO,
                                          @PathVariable String locale) {
        RegistrationResponseDTO result = userService.registration(inputDTO);

        var tokens = tokenService.authenticateAndGenerate(inputDTO.getEmail(), inputDTO.getPassword());
        var cookies = tokenCookieService.buildCookies(tokens);

        ResponseEntity<?> response = inertia.render("Users/Dashboard",
                Map.of("user", result, "locale", locale));

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(response.getHeaders())
                .header(HttpHeaders.SET_COOKIE, cookies.access().toString())
                .header(HttpHeaders.SET_COOKIE, cookies.refresh().toString())
                .body(response.getBody());
    }
}
