package io.hexlet.cv.security;

import io.hexlet.cv.handler.exception.InvalidTokenTypeException;
import io.hexlet.cv.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    public static final String REFRESH_TOKEN_NAME = "refresh";

    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;

    public Tokens authenticateAndGenerate(String email, String password) {
        var authentication = new UsernamePasswordAuthenticationToken(email, password);
        authenticationManager.authenticate(authentication);

        var accessToken = jwtUtils.generateAccessToken(email);
        var refreshToken = jwtUtils.generateRefreshToken(email);

        return new Tokens(accessToken, refreshToken);
    }

    public String refreshAccessToken(String refreshToken) {
        Jwt decoded = jwtUtils.decode(refreshToken);

        String tokenType = decoded.getClaimAsString("type");
        if (!REFRESH_TOKEN_NAME.equals(tokenType)) {
            throw new InvalidTokenTypeException("Expected refresh token, got: " + tokenType);
        }

        String email = decoded.getSubject();
        return jwtUtils.generateAccessToken(email);
    }

    public record Tokens(String access, String refresh) {

    }
}
