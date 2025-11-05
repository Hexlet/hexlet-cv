package io.hexlet.cv.exception;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.reset.EmailForm;
import io.hexlet.cv.dto.reset.LoginForm;
import io.hexlet.cv.dto.reset.PasswordResetForm;
import io.hexlet.cv.dto.reset.RegisterForm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final Inertia inertia;

    @ExceptionHandler(AuthenticationException.class)
    public String handleAuthenticationException(AuthenticationException e,
                                                HttpServletRequest request) {
        return renderWithErrors("Auth/Login",
                Map.of("auth", e.getMessage()),
                new LoginForm(),
                request
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExistsException(UserAlreadyExistsException e,
                                                   HttpServletRequest request) {
        return renderWithErrors("Auth/Register",
                Map.of("email", "User with this email already exists"),
                new RegisterForm(),
                request
        );
    }

    @ExceptionHandler(InvalidTokenException.class)
    public String handleInvalidTokenException(InvalidTokenException e,
                                              HttpServletRequest request) {
        return renderWithErrors("Auth/ResetPassword",
                Map.of("token", e.getMessage()),
                new PasswordResetForm(),
                request
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException e,
                                              HttpServletRequest request) {
        return "redirect:/auth/login?message=reset_email_sent";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationException(MethodArgumentNotValidException ex,
                                            HttpServletRequest request) {
        String component = determineFormComponent(request);
        Object form = createEmptyForm(component);

        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage
                ));

        return renderWithErrors(component, errors, form, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(AccessDeniedException ex,
                                     HttpServletRequest request) {
        return renderWithErrors("Auth/Login",
                Map.of("auth", "Access denied. Please login again."),
                new LoginForm(),
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception ex,
                                      HttpServletRequest request) {
        ex.printStackTrace();

        return render("Error", Map.of(
                "message", "Something went wrong. Please try again.",
                "csrf", getCsrfToken(request)
        ));
    }

    private String render(String component, Map<String, Object> props) {
        ResponseEntity<String> response = inertia.render(component, props);
        return String.valueOf(response.getBody());
    }

    private String renderWithErrors(String component,
                                    Map<String, String> errors,
                                    Object form,
                                    HttpServletRequest request) {
        return render(component, Map.of(
                "errors", errors,
                "form", form,
                "csrf", getCsrfToken(request)
        ));
    }

    private String determineFormComponent(HttpServletRequest request) {
        String path = request.getServletPath();
        if (path.contains("/register")) return "Auth/Register";
        if (path.contains("/reset")) return "Auth/ResetPassword";
        if (path.contains("/forgot")) return "Auth/ForgotPassword";
        return "Auth/Login"; // по умолчанию
    }

    private Object createEmptyForm(String component) {
        return switch (component) {
            case "Auth/Register" -> new RegisterForm();
            case "Auth/ResetPassword" -> new PasswordResetForm();
            case "Auth/ForgotPassword" -> new EmailForm();
            default -> new LoginForm();
        };
    }

    private String getCsrfToken(HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return csrfToken != null ? csrfToken.getToken() : "";
    }
}
