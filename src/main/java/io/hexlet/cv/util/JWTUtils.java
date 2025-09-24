package io.hexlet.cv.util;


import io.hexlet.cv.config.JwtProperties;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

@Component
public class JWTUtils {

    @Autowired
    private final JwtEncoder encoder;
    private final JwtProperties jwtProperties;

    public JWTUtils(JwtEncoder encoder, JwtProperties jwtProperties) {
        this.encoder = encoder;
        this.jwtProperties = jwtProperties;
    }

    public String generateAccessToken(String username) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(jwtProperties.getAccessTokenValiditySeconds(), ChronoUnit.SECONDS))

                .subject(username)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateRefreshToken(String username) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(jwtProperties.getRefreshTokenValiditySeconds(), ChronoUnit.SECONDS))
                .subject(username)
                .claim("type", "refresh") // можно явно указать тип
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
