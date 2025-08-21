package io.hexlet.cv.handler;

import io.hexlet.cv.handler.exception.InvalidPasswordException;
import io.hexlet.cv.handler.exception.UserAlreadyExistsException;
import io.hexlet.cv.handler.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request,
                                   RedirectAttributes redirectAttributes) {

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
// если заголовок "X-Inertia: true" то это Inertia
        if ("true".equals(request.getHeader("X-Inertia"))) {
            redirectAttributes.addFlashAttribute("errors", errors);
            String referer = request.getHeader("Referer");
            return "redirect:" + (referer != null ? referer : "/");
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of("errors", errors));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public Object handleUserAlreadyExists(UserAlreadyExistsException ex, HttpServletRequest request,
                                          RedirectAttributes redirectAttributes) {

        Map<String, String> errors = Map.of("email", ex.getMessage());
        if ("true".equals(request.getHeader("X-Inertia"))) {
            redirectAttributes.addFlashAttribute("errors", errors);
            String referer = request.getHeader("Referer");
            return "redirect:" + (referer != null ? referer : "/");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("errors", errors));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public Object handleUserNotFound(UserNotFoundException ex, HttpServletRequest request,
                                        RedirectAttributes redirectAttributes) {

        Map<String, String> errors = Map.of("email", ex.getMessage());
        if ("true".equals(request.getHeader("X-Inertia"))) {
            redirectAttributes.addFlashAttribute("errors", errors);
            String referer = request.getHeader("Referer");
            return "redirect:" + (referer != null ? referer : "/");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("errors", errors));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public Object handleInvalidPasswordException(InvalidPasswordException ex, HttpServletRequest request,
                                     RedirectAttributes redirectAttributes) {

        Map<String, String> errors = Map.of("password", ex.getMessage());
        if ("true".equals(request.getHeader("X-Inertia"))) {
            redirectAttributes.addFlashAttribute("errors", errors);
            String referer = request.getHeader("Referer");
            return "redirect:" + (referer != null ? referer : "/");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("errors", errors));
    }

// это просто ошибки все остальное
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex) {
        Map<String, String> errors = Map.of("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("errors", errors));
    }
}
