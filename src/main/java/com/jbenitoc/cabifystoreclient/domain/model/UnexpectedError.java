package com.jbenitoc.cabifystoreclient.domain.model;

public class UnexpectedError extends RuntimeException {
    public UnexpectedError(String message, Throwable cause) {
        super(message, cause);
    }
}
