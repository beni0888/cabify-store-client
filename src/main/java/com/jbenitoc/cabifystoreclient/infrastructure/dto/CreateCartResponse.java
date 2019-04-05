package com.jbenitoc.cabifystoreclient.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateCartResponse {
    public final String id;

    @JsonCreator
    public CreateCartResponse(@JsonProperty("id") String id) {
        this.id = id;
    }
}
