package io.hexlet.cv.exception;

public class WeakPasswordException extends RuntimeException {

    public WeakPasswordException(String message) {
        super(message);
    }
}
