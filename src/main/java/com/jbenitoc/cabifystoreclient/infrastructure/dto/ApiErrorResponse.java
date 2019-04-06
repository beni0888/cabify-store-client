package com.jbenitoc.cabifystoreclient.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiErrorResponse {
    public final String message;

    @JsonCreator
    public ApiErrorResponse(@JsonProperty("message") String message) {
        this.message = message;
    }
}
