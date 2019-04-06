package com.jbenitoc.cabifystoreclient.domain.model;

public class DomainError extends RuntimeException {
    public DomainError(String message) {
        super(message);
    }
}
