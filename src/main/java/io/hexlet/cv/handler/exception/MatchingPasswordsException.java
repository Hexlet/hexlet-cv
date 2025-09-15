package io.hexlet.cv.handler.exception;

public class MatchingPasswordsException extends RuntimeException {
    public MatchingPasswordsException(String massage) {
        super(massage);
    }
}
