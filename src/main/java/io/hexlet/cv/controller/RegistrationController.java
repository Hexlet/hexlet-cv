
package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.user.auth.RegistrationRequestDTO;
import io.hexlet.cv.security.TokenCookieService;
import io.hexlet.cv.security.TokenService;
import io.hexlet.cv.service.FlashPropsService;
import io.hexlet.cv.service.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.Locale;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
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
    // private final AuthResponseService authResponseService;
    private final FlashPropsService flashPropsService;

    private final TokenCookieService tokenCookieService;

    private MessageSource messageSource;

    @GetMapping("/{locale}/users/sign_up")
    public ResponseEntity<?> signUpForm(@PathVariable String locale,
                                        HttpServletRequest request) {

        var props = flashPropsService.buildProps(locale, request);

        return inertia.render("Users/SignUp", props);
    }

    @PostMapping(path = "/{locale}/users")
    public Object registration(@Valid @RequestBody RegistrationRequestDTO inputDTO,
                               @PathVariable String locale,
                               HttpServletResponse response,
                               HttpSession session) {

        userService.registration(inputDTO);

        var tokens = tokenService.authenticateAndGenerate(
                inputDTO.getEmail(),
                inputDTO.getPassword()
        );

        // ------

        var access = tokenCookieService.buildAccessCookie(tokens.access());
        var refresh = tokenCookieService.buildRefreshCookie(tokens.refresh());

        response.addHeader(HttpHeaders.SET_COOKIE, access.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refresh.toString());

        /*
        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .header(HttpHeaders.LOCATION, "/" + locale + "/dashboard")
                .build();
        */

        String successMessage = messageSource.getMessage(
                "registration.success",
                null,
                new Locale(locale)
        );

        session.setAttribute("flash", Map.of("success", successMessage));

        return inertia.redirect("/" + locale + "/dashboard");


// ----------
        // return authResponseService.success(locale, tokens, response);
    }
}
