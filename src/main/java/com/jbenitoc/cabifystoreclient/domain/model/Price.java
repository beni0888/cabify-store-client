package com.jbenitoc.cabifystoreclient.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Price {
    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
