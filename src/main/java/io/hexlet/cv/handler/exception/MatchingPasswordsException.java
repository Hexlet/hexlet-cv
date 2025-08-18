package io.hexlet.cv.handler.exception;

import io.hexlet.cv.dto.user.UserPasswordDto;

public class MatchingPasswordsException extends RuntimeException {

    public MatchingPasswordsException(String massage) {
        super(massage);
    }
}
