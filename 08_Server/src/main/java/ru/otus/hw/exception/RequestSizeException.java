package ru.otus.hw.exception;

public class RequestSizeException extends RuntimeException {
    public RequestSizeException(String message) {
        super(message);
    }
}
