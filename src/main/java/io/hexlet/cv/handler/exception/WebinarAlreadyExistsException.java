package io.hexlet.cv.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class WebinarAlreadyExistsException extends RuntimeException {

    public WebinarAlreadyExistsException(String message) {
        super(message);
    }
}
