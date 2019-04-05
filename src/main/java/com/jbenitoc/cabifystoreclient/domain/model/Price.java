package com.jbenitoc.cabifystoreclient.domain.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Price {
    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
