
package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.security.TokenCookieService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@AllArgsConstructor
public class LogoutController {

    private final TokenCookieService tokenCookieService;

    private MessageSource messageSource;
    private final Inertia inertia;

    @PostMapping("/users/sign_out")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // HttpSession session) {

        var expiredAccess = tokenCookieService.buildExpiredAccessCookie();
        var expiredRefresh = tokenCookieService.buildExpiredRefreshCookie();

        response.addHeader(HttpHeaders.SET_COOKIE, expiredAccess.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, expiredRefresh.toString());

/*
        String successMessage = messageSource.getMessage(
                "logout.success",
                null,
                new Locale(locale)
        );


        session.setAttribute("flash", Map.of("success", successMessage));
*/

        return inertia.redirect("/");

        //  return ResponseEntity.status(HttpStatus.SEE_OTHER)
        //          .header(HttpHeaders.LOCATION, "/" + locale)
        //         .build();

        // return authResponseService.logoutSuccess(locale, response);
    }
}
