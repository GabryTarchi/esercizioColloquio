package com.example.esercizioColloquio.exception;

public class RequestEmptyException extends RuntimeException {
    public RequestEmptyException(String message) {
        super(message);
    }
}
