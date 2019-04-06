package com.jbenitoc.cabifystoreclient.infrastructure.service;

import com.jbenitoc.cabifystoreclient.domain.model.ElementDoesNotExist;
import com.jbenitoc.cabifystoreclient.domain.model.RequestIsNotValid;
import com.jbenitoc.cabifystoreclient.domain.model.ServerError;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.ApiErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ApiResponseHandler {

    private JsonParser jsonParser;

    public  <T> T parseApiResponse(ResponseEntity<String> response, Class<T> targetClass) {
        if (response.getStatusCode().is2xxSuccessful()) {
            if (response.getBody() == null) {
                return null;
            }

            return jsonParser.parseStringToObject(response.getBody(), targetClass);
        }

        ApiErrorResponse errorResponse = jsonParser.parseStringToObject(response.getBody(), ApiErrorResponse.class);
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new ElementDoesNotExist(errorResponse.message);
        }
        if(response.getStatusCode().is4xxClientError()) {
            throw new RequestIsNotValid(errorResponse.message);
        }
        throw new ServerError(errorResponse.message);
    }
}
