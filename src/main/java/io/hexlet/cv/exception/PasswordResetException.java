package io.hexlet.cv.exception;

public class PasswordResetException extends RuntimeException {

    public PasswordResetException (String message) {
        super(message);
    }
}
