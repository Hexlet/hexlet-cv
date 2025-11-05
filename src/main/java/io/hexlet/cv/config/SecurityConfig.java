package io.hexlet.cv.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/auth/password/forgot",
//                                "/auth/password/reset")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .sessionFixation().migrateSession()
                        .maximumSessions(1)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .successHandler(inertiaSuccessHandler())
                        .failureHandler(inertiaFailureHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessHandler(inertiaLogoutSuccessHandler())
                        .permitAll()
                )
                .build();
    }

    @Bean
    public AuthenticationSuccessHandler inertiaSuccessHandler() {
        return (request, response, authentication) -> {
            if (isInertiaRequest(request)) {
                response.setStatus(200);
            } else {
                response.sendRedirect("/dashboard");
            }
        };
    }

    @Bean
    public AuthenticationFailureHandler inertiaFailureHandler() {
        return (request, response, exception) -> {
            if (isInertiaRequest(request)) {
                response.setStatus(401);
            } else {
                response.sendRedirect("/auth/login?error=true");
            }
        };
    }

    @Bean
    public LogoutSuccessHandler inertiaLogoutSuccessHandler() {
        return (request, response, authentication) -> {
            if (isInertiaRequest(request)) {
                response.setStatus(200);
            } else {
                response.sendRedirect("/auth/login?logout=true");
            }
        };
    }

    private boolean isInertiaRequest(HttpServletRequest request) {
        return "true".equals(request.getHeader("X-Inertia"));
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
