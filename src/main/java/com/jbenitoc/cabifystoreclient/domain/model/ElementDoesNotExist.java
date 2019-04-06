package com.jbenitoc.cabifystoreclient.domain.model;

public class ElementDoesNotExist extends DomainError {
    public ElementDoesNotExist(String message) {
        super(message);
    }
}
