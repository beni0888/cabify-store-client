package com.jbenitoc.cabifystoreclient.infrastructure.service;

import com.jbenitoc.cabifystoreclient.domain.model.ElementDoesNotExist;
import com.jbenitoc.cabifystoreclient.domain.model.RequestIsNotValid;
import com.jbenitoc.cabifystoreclient.domain.model.ServerError;
import com.jbenitoc.cabifystoreclient.domain.model.UnexpectedError;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.ApiErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ApiResponseHandler {

    private JsonParser jsonParser;

    public  <T> T parseToObject(ResponseEntity<String> response, Class<T> targetClass) {
        if (response.getStatusCode().is2xxSuccessful()) {
            if (!hasBody(response)) {
                return null;
            }

            return jsonParser.parseStringToObject(response.getBody(), targetClass);
        }

        ApiErrorResponse errorResponse = parseErrorResponse(response);
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new ElementDoesNotExist(errorResponse.message);
        }
        if(response.getStatusCode().is4xxClientError()) {
            throw new RequestIsNotValid(errorResponse.message);
        }
        throw new ServerError(errorResponse.message);
    }

    private ApiErrorResponse parseErrorResponse(ResponseEntity<String> response) {
        if (!hasBody(response)) {
            throw unknownError(response.getStatusCode());
        }

        try {
            return jsonParser.parseStringToObject(response.getBody(), ApiErrorResponse.class);
        } catch (Exception e) {
            throw unexpectedError(response.getStatusCode(), e);
        }
    }

    private RuntimeException unexpectedError(HttpStatus status, Exception e) {
        if (status == HttpStatus.NOT_FOUND) {
            return new ElementDoesNotExist(e.getMessage());
        }
        return new UnexpectedError(e.getMessage(), e);
    }

    private RuntimeException unknownError(HttpStatus status) {
        final String message = "Unknown error";
        if (status == HttpStatus.NOT_FOUND) {
            return new ElementDoesNotExist(message);
        }
        return new ServerError(message);
    }

    private boolean hasBody(ResponseEntity<String> response) {
        return response.getBody() != null && !(response.getBody().isEmpty());
    }
}
