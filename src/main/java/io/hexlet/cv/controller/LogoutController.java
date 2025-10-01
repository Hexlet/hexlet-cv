package io.hexlet.cv.controller;

import io.hexlet.cv.security.AuthResponseService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LogoutController {
    private final AuthResponseService authResponseService;

    @PostMapping("/{locale}/users/sign_out")
    public ResponseEntity<Void> logout(@PathVariable String locale,
                                       HttpServletResponse response) {

        return authResponseService.logoutSuccess(locale, response);
    }
}
