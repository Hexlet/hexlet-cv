package io.hexlet.cv.exception;

public class EmailSendingException extends RuntimeException {

    public EmailSendingException (String message) {
        super(message);
    }
}
