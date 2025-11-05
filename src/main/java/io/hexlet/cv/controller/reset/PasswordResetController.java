package io.hexlet.cv.controller.reset;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.reset.EmailForm;
import io.hexlet.cv.dto.reset.PasswordResetForm;
import io.hexlet.cv.exception.UserNotFoundException;
import io.hexlet.cv.service.EmailService;
import io.hexlet.cv.service.PasswordResetService;
import io.hexlet.cv.util.PasswordGeneratorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auth/password")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;
    private final PasswordGeneratorService passwordGeneratorService;
    private final EmailService emailService;
    private final Inertia inertia;

    @GetMapping("/forgot")
    public String forgotPasswordPage(HttpServletRequest request, Model model) {
        ResponseEntity<String> response = render("Auth/ForgotPassword", Map.of(
                "errors", new HashMap<>(),
                "form", new EmailForm(),
                "csrf", getCsrfToken(request)
        ));

        model.addAttribute("page", response.getBody());
        return "app";
    }

    @PostMapping("/forgot")
    public ResponseEntity<String> sendResetEmail(@Valid EmailForm form,
                                                 BindingResult bindingResult,
                                                 HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return render("Auth/ForgotPassword", Map.of(
                    "errors", getValidationErrors(bindingResult),
                    "form", form,
                    "csrf", getCsrfToken(request)
            ));
        }

        try {
            String clientUrl = generateClientUrl(request);
            emailService.sendResetEmail(form.getEmail(), clientUrl);

            return ResponseEntity.status(302)
                    .header("Location", "/auth/login?message=reset_email_sent")
                    .build();

        } catch (UserNotFoundException e) {
            System.out.println("Password reset attempt for non-existent email: " + form.getEmail());
            return ResponseEntity.status(302)
                    .header("Location", "/auth/login?message=reset_email_sent")
                    .build();
        } catch (Exception e) {
            return render("Auth/ForgotPassword", Map.of(
                    "errors", Map.of("email", "Error sending reset email. Please try again."),
                    "form", form,
                    "csrf", getCsrfToken(request)
            ));
        }
    }

    public String resetPasswordPage(@RequestParam String token,
                                    HttpServletRequest request,
                                    Model model) {

        boolean isValid = passwordResetService.isTokenValid(token);
        Map<String, Object> props = new HashMap<>();
        props.put("token", token);
        props.put("isValid", isValid);
        props.put("errors", new HashMap<>());
        props.put("form", new PasswordResetForm());
        props.put("csrf", getCsrfToken(request));

        if (!isValid) {
            props.put("errors", Map.of("token", "Invalid or expired reset link"));
        }

        ResponseEntity<String> response = render("Auth/ResetPassword", props);
        model.addAttribute("page", response.getBody());
        return "app";
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam String token,
                                                @Valid PasswordResetForm form,
                                                BindingResult bindingResult,
                                                HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return renderWithToken("Auth/ResetPassword", token, form, bindingResult, request);
        }

        if (!passwordResetService.isTokenValid(token)) {
            return renderWithToken("Auth/ResetPassword", token, form,
                    Map.of("token", "Invalid or expired reset link"), request);
        }

        String newPassword = form.isAutoGenerate() ?
                passwordGeneratorService.generateStrongPassword() : form.getPassword();

        String userEmail = passwordResetService.resetPassword(token, newPassword);

        if (form.isAutoGenerate()) {
            emailService.sendNewPasswordEmail(userEmail, newPassword);
        }

        return ResponseEntity.status(302)
                .header("Location", "/auth/login?message=password_reset_success")
                .build();
    }

    private ResponseEntity<String> render(String component, Map<String, Object> props) {
        return inertia.render(component, props);
    }

    private ResponseEntity<String> renderWithToken(String component, String token, Object form,
                                                   BindingResult bindingResult, HttpServletRequest request) {
        return render(component, Map.of(
                "token", token,
                "form", form,
                "errors", getValidationErrors(bindingResult),
                "csrf", getCsrfToken(request)
        ));
    }

    private ResponseEntity<String> renderWithToken(String component, String token, Object form,
                                                   Map<String, String> errors, HttpServletRequest request) {
        return render(component, Map.of(
                "token", token,
                "form", form,
                "errors", errors,
                "csrf", getCsrfToken(request)
        ));
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

    private String generateClientUrl(HttpServletRequest request) {
        String baseUrl = request.getRequestURL().toString()
                .replace(request.getServletPath(), "");
        return baseUrl + "/auth/password/reset";
    }

}
