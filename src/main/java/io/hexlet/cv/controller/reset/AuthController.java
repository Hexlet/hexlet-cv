package io.hexlet.cv.controller.reset;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.reset.RegisterForm;
import io.hexlet.cv.exception.UserAlreadyExistsException;
import io.hexlet.cv.exception.WeakPasswordException;
import io.hexlet.cv.model.User;
import io.hexlet.cv.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final Inertia inertia;

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request, Model model) {
        ResponseEntity<String> response = render("Auth/Login", Map.of(
                "errors", new HashMap<>(),
                "csrf", getCsrfToken(request)
        ));
        String html = response.getBody();
        String json = extractPageJsonFromHtml(html);
        model.addAttribute("page", json);
        return "app";
    }

    @GetMapping("/register")
    public String registerPage(HttpServletRequest request, Model model) {
        ResponseEntity<String> response = render("Auth/Register", Map.of(
                "errors", new HashMap<>(),
                "csrf", getCsrfToken(request)
        ));

        model.addAttribute("page", response.getBody());  // ← ДОБАВИТЬ эту строку
        return "app";  // ← ВЕРНУТЬ "app" вместо response
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid RegisterForm form,
                                           BindingResult bindingResult,
                                           HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return render("Auth/Register", Map.of(
                    "errors", getValidationErrors(bindingResult),
                    "form", form,
                    "csrf", getCsrfToken(request)
            ));
        }

        try {
            User user = authService.register(form.getEmail(), form.getPassword());
            // ✅ Редирект через ResponseEntity
            return ResponseEntity.status(302)
                    .header("Location", "/auth/login?message=registration_success")
                    .build();

        } catch (WeakPasswordException e) {
            return render("Auth/Register", Map.of(
                    "errors", Map.of("password", "This password is too common. Please choose a stronger one."),
                    "form", form,
                    "csrf", getCsrfToken(request)
            ));
        } catch (UserAlreadyExistsException e) {
            return render("Auth/Register", Map.of(
                    "errors", Map.of("email", "User with this email already exists"),
                    "form", form,
                    "csrf", getCsrfToken(request)
            ));
        }
    }

    private ResponseEntity<String> render(String component, Map<String, Object> props) {
        return inertia.render(component, props);
    }

    private Map<String, String> getValidationErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage
                ));
    }

    private String getCsrfToken(HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return csrfToken != null ? csrfToken.getToken() : "";
    }

    private String extractPageJsonFromHtml(String html) {
        Pattern pattern = Pattern.compile("data-page='(.*?)'");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "{}";
    }

}

