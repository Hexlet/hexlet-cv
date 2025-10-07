package io.hexlet.cv.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTokenTypeException extends RuntimeException {
    public InvalidTokenTypeException(String message) {
        super(message);
    }
}
