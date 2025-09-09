package io.hexlet.cv.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthResponseService {

    private final TokenCookieService tokenCookieService;

    public ResponseEntity<Void> success(String locale,
                                        TokenService.Tokens tokens,
                                        HttpServletResponse response) {

        var access = tokenCookieService.buildAccessCookie(tokens.access());
        var refresh = tokenCookieService.buildRefreshCookie(tokens.refresh());

        response.addHeader(HttpHeaders.SET_COOKIE, access.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refresh.toString());

        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .header(HttpHeaders.LOCATION, "/" + locale + "/dashboard")
                .build();
    }
}
