package com.jbenitoc.cabifystoreclient.infrastructure.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbenitoc.cabifystoreclient.domain.model.UnexpectedError;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@AllArgsConstructor
@Service
public class JsonParser {

    private ObjectMapper mapper;

    public  <T> T parseStringToObject(String jsonString, Class<T> targetClass) {
        try {
            return mapper.readValue(jsonString, targetClass);
        } catch (IOException e) {
            throw new UnexpectedError("Error parsing JSON string", e);
        }
    }
}
