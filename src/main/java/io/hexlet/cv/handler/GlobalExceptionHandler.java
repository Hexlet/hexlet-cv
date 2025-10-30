package io.hexlet.cv.handler;

import io.hexlet.cv.handler.exception.InvalidPasswordException;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.handler.exception.UserAlreadyExistsException;
import io.hexlet.cv.handler.exception.UserNotFoundException;
import io.hexlet.cv.handler.exception.WebinarAlreadyExistsException;
import jakarta.persistence.EntityExistsException;
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
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Object commonHandle(Map<String, String> errors,
                                HttpServletRequest request,
                                RedirectAttributes redirectAttributes,
                                HttpStatus status) {

        // Обработка AJAX-запроса (Inertia)
        if ("true".equals(request.getHeader("X-Inertia"))) {
            redirectAttributes.addFlashAttribute("errors", errors);
            String referer = request.getHeader("Referer");
            RedirectView redirectView = new RedirectView(referer != null ? referer : "/");
            redirectView.setHttp10Compatible(false);
            redirectView.setStatusCode(HttpStatus.SEE_OTHER);
            return redirectView;
        }

        // Обработка обычного запроса
        return ResponseEntity.status(status).body(Map.of("errors", errors));
    }

    @ExceptionHandler(EntityExistsException.class)
    public Object handleEntityExistsException(EntityExistsException ex,
                                              HttpServletRequest request,
                                              RedirectAttributes redirectAttributes) {

        Map<String, String> errors = Map.of("error", ex.getMessage());
        return commonHandle(errors, request, redirectAttributes, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public Object handleResourceNotFoundException(ResourceNotFoundException ex,
                                                  HttpServletRequest request,
                                                  RedirectAttributes redirectAttributes) {

        Map<String, String> errors = Map.of("error", ex.getMessage());
        return commonHandle(errors, request, redirectAttributes, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(WebinarAlreadyExistsException.class)
    public Object handleWebinarAlreadyExistsException(WebinarAlreadyExistsException ex,
                                                      HttpServletRequest request,
                                                      RedirectAttributes redirectAttributes) {

        Map<String, String> errors = Map.of("error", ex.getMessage());
        return commonHandle(errors, request, redirectAttributes, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleValidation(MethodArgumentNotValidException ex,
                                   HttpServletRequest request,
                                   RedirectAttributes redirectAttributes) {

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return commonHandle(errors, request, redirectAttributes, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public Object handleUserAlreadyExists(UserAlreadyExistsException ex,
                                          HttpServletRequest request,
                                          RedirectAttributes redirectAttributes) {

        Map<String, String> errors = Map.of("email", ex.getMessage());
        return commonHandle(errors, request, redirectAttributes, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public Object handleUserNotFound(UserNotFoundException ex,
                                     HttpServletRequest request,
                                     RedirectAttributes redirectAttributes) {

        Map<String, String> errors = Map.of("email", ex.getMessage());
        return commonHandle(errors, request, redirectAttributes, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(InvalidPasswordException.class)
    public Object handleInvalidPasswordException(InvalidPasswordException ex,
                                                 HttpServletRequest request,
                                                 RedirectAttributes redirectAttributes) {

        Map<String, String> errors = Map.of("password", ex.getMessage());
        return commonHandle(errors, request, redirectAttributes, HttpStatus.UNAUTHORIZED);
    }


// это просто ошибки все остальное
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAll(Exception ex) {
        Map<String, String> errors = Map.of("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("errors", errors));
    }
}
