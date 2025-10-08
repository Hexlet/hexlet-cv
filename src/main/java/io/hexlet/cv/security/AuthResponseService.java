
/*package io.hexlet.cv.security;





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

public ResponseEntity<Void> logoutSuccess(String locale,
                                          HttpServletResponse response) {

    var expiredAccess = tokenCookieService.buildExpiredAccessCookie();
    var expiredRefresh = tokenCookieService.buildExpiredRefreshCookie();

    response.addHeader(HttpHeaders.SET_COOKIE, expiredAccess.toString());
    response.addHeader(HttpHeaders.SET_COOKIE, expiredRefresh.toString());

    return ResponseEntity.status(HttpStatus.SEE_OTHER)
            .header(HttpHeaders.LOCATION, "/" + locale)
            .build();
}


}
 */
