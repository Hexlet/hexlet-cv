package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.user.auth.LoginRequestDTO;
import io.hexlet.cv.security.TokenCookieService;
import io.hexlet.cv.security.TokenService;
import io.hexlet.cv.service.FlashPropsService;
import io.hexlet.cv.service.LoginService;
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
public class LoginController {

    private final Inertia inertia;
    private final LoginService loginService;
    private final TokenService tokenService;
    //   private final AuthResponseService authResponseService;
    private final FlashPropsService flashPropsService;


    private final TokenCookieService tokenCookieService;

    private MessageSource messageSource;

    @GetMapping("/{locale}/users/sign_in")
    public ResponseEntity<?> signInForm(@PathVariable String locale,
                                        HttpServletRequest request) {



        var props = flashPropsService.buildProps(locale, request);

        return inertia.render("Users/SignIn", props);
    }

    @PostMapping(path = "/{locale}/users/sign_in")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginDTO,
                                   @PathVariable String locale,
                                   HttpServletResponse response,
                                   HttpSession session) {




        loginService.login(loginDTO);
        var tokens = tokenService.authenticateAndGenerate(
                loginDTO.getEmail(),
                loginDTO.getPassword()
        );



        var access = tokenCookieService.buildAccessCookie(tokens.access());
        var refresh = tokenCookieService.buildRefreshCookie(tokens.refresh());

        response.addHeader(HttpHeaders.SET_COOKIE, access.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refresh.toString());

        //   return ResponseEntity.status(HttpStatus.SEE_OTHER)
        //          .header(HttpHeaders.LOCATION, "/" + locale + "/dashboard")
        //         .build();

        String successMessage = messageSource.getMessage(
                "login.success",
                null,
                new Locale(locale)
        );

        session.setAttribute("flash", Map.of("success", successMessage));

        return inertia.redirect("/" + locale + "/dashboard");

        //  return authResponseService.success(locale, tokens, response);
    }
}