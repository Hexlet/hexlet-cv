package io.hexlet.cv.config;

import io.hexlet.cv.service.CustomUserDetailsService;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain security(HttpSecurity http,
                                 JwtDecoder jwtDecoder,
                                 BearerTokenResolver cookieTokenResolver,
                                 Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthConverter)
            throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                     //   .requestMatchers("/admin/**", "/*/admin/**", "/*/admin/").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(rs -> rs
                        .bearerTokenResolver(cookieTokenResolver)
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder)                       // берётся из EncodersConfig
                                .jwtAuthenticationConverter(jwtAuthConverter)
                        ));
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthProvider(CustomUserDetailsService userService,
                                                     PasswordEncoder passwordEncoder) {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(DaoAuthenticationProvider daoAuthProvider) {
        return new ProviderManager(daoAuthProvider);
    }

    @Bean
    public BearerTokenResolver cookieTokenResolver() {
        return request -> {
            var cookies = request.getCookies();
            if (cookies == null) {
                return null;
            }
            for (var c : cookies) {
                if ("access_token".equals(c.getName())) {
                    return c.getValue();
                }
            }
            return null;
        };
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthConverter() {
        return jwt -> {
            Set<GrantedAuthority> authorities = new HashSet<>();

            addRoles(authorities, jwt.getClaim("roles"));

            Map<String, Object> realm = jwt.getClaim("realm_access");
            if (realm != null) {
                addRoles(authorities, realm.get("roles"));
            }

            addRoles(authorities, jwt.getClaim("authorities"));

            return new JwtAuthenticationToken(jwt, authorities, jwt.getSubject());
        };
    }

    private static void addRoles(Set<GrantedAuthority> out, Object claim) {
        if (claim instanceof Collection<?> col) {
            for (Object v : col) {
                String role = String.valueOf(v);
                if (!role.startsWith("ROLE_")) {
                    role = "ROLE_" + role;
                }
                out.add(new SimpleGrantedAuthority(role));
            }
        }
    }
}
