package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.user.UserPasswordDto;
import io.hexlet.cv.security.TokenCookieService;
import io.hexlet.cv.security.TokenService;
import io.hexlet.cv.service.FlashPropsService;
import io.hexlet.cv.service.PasswordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@AllArgsConstructor
public class PasswordController {

    private final Inertia inertia;
    private final PasswordService passwordService;
    private final FlashPropsService flashPropsService;
    private final TokenCookieService tokenCookieService;
    private final TokenService tokenService;

    @GetMapping(path = "/{locale}/users/password")
    public ResponseEntity<?> getChangeAccountPassword(@PathVariable String locale,
                                                      HttpServletRequest request) {

        var props = flashPropsService.buildProps(locale, request);

        return inertia.render("Users/Password", props);
    }

    @PatchMapping(path = "/{locale}/users/password")
    public ResponseEntity<?> ChangeAccountPassword(@Valid @RequestBody UserPasswordDto userPasswordDto,
                                                   @PathVariable String locale,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) {
        passwordService.passwordChange(userPasswordDto);

        String refreshToken = passwordService.extractRefreshToken(request);
        String accessToken = tokenService.refreshAccessToken(refreshToken);
        ResponseCookie access = tokenCookieService.buildAccessCookie(accessToken);
        response.addHeader(HttpHeaders.SET_COOKIE, access.toString());

        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .header(HttpHeaders.LOCATION, "/" + locale + "/users")
                .build();
    }
}
