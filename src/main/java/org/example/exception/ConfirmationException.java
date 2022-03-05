package org.example.exception;

public class ConfirmationException extends RuntimeException {
    public ConfirmationException() {
    }

    public ConfirmationException(String message) {
        super(message);
    }
}
