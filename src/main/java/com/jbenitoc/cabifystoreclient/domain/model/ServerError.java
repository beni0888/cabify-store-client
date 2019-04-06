package com.jbenitoc.cabifystoreclient.domain.model;

public class ServerError extends RuntimeException {
    public ServerError(String message) {
        super(message);
    }
}
