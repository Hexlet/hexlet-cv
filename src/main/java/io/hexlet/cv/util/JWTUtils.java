package io.hexlet.cv.util;


import io.hexlet.cv.config.JwtProperties;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import io.hexlet.cv.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JWTUtils {

    private final JwtEncoder encoder;
    private final JwtProperties jwtProperties;

    private final UserRepository userRepository; // нужен доступ к роли

    /*
    public JWTUtils(JwtEncoder encoder, JwtProperties jwtProperties, UserRepository userRepository) {
        this.encoder = encoder;
        this.jwtProperties = jwtProperties;
        this.userRepository = userRepository;
    }
*/

    public String generateAccessToken(String username) {

        var user = userRepository.findByEmail(username).orElseThrow();
        var role = user.getRole().name(); // например ADMIN

        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(jwtProperties.getAccessTokenValiditySeconds(), ChronoUnit.SECONDS))
                .subject(username)
                .claim("roles", List.of(role))
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
