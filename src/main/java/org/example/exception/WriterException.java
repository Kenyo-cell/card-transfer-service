package org.example.exception;

public class WriterException extends RuntimeException {
    public WriterException() {
    }

    public WriterException(String message) {
        super(message);
    }
}
