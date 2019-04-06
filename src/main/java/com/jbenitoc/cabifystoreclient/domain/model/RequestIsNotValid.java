package com.jbenitoc.cabifystoreclient.domain.model;

public class RequestIsNotValid extends DomainError {
    public RequestIsNotValid(String message) {
        super(message);
    }
}
