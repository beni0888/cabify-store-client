package com.jbenitoc.cabifystoreclient.infrastructure.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbenitoc.cabifystoreclient.domain.model.ElementDoesNotExist;
import com.jbenitoc.cabifystoreclient.domain.model.RequestIsNotValid;
import com.jbenitoc.cabifystoreclient.domain.model.ServerError;
import com.jbenitoc.cabifystoreclient.domain.model.UnexpectedError;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.ApiErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ApiResponseHandlerTest {

    private JsonParser jsonParser;
    private ApiResponseHandler responseHandler;

    @BeforeEach
    void setUp() {
        jsonParser = mock(JsonParser.class);
        responseHandler = new ApiResponseHandler(jsonParser);
    }

    @Test
    void givenAnOkResponse_whenParseToObject_thenAnObjectResultingFromResponseBodyUnmarshallingIsReturned() {
        ResponseEntity<String> apiResponse = apiResponse(HttpStatus.OK,"whatever");
        when(jsonParser.parseStringToObject(eq(apiResponse.getBody()), any(Class.class)))
                .thenReturn(new Object());

        Object result = responseHandler.parseToObject(apiResponse, Object.class);

        verify(jsonParser, times(1)).parseStringToObject(any(), any());
        assertThat(result).isNotNull();
    }

    @Test
    void givenACreatedResponse_whenParseToObject_thenAnObjectResultingFromResponseBodyUnmarshallingIsReturned() {
        ResponseEntity<String> apiResponse = apiResponse(HttpStatus.CREATED,"whatever");
        when(jsonParser.parseStringToObject(eq(apiResponse.getBody()), any(Class.class)))
                .thenReturn(new Object());

        Object result = responseHandler.parseToObject(apiResponse, Object.class);

        verify(jsonParser, times(1)).parseStringToObject(any(), any());
        assertThat(result).isNotNull();
    }

    @Test
    void givenACreatedResponseWithoutBody_whenParseToObject_thenNullIsReturned() {
        ResponseEntity apiResponse = apiResponseWithoutBody(HttpStatus.CREATED);

        Object result = responseHandler.parseToObject(apiResponse, Object.class);

        verify(jsonParser, times(0)).parseStringToObject(any(), any());
        assertThat(result).isNull();
    }

    @Test
    void givenANotFoundResponse_whenParseToObject_thenAnElementDoesNotExistWithProperMessageIsThrown() {
        String errorMessage = "Cart does not exist";
        ResponseEntity<String> apiResponse = apiResponse(HttpStatus.NOT_FOUND, errorMessage);
        when(jsonParser.parseStringToObject(eq(apiResponse.getBody()), any(Class.class)))
                .thenReturn(apiErrorResponse(errorMessage));

        Exception e = assertThrows(ElementDoesNotExist.class, () -> responseHandler.parseToObject(apiResponse, Object.class));

        verify(jsonParser, times(1)).parseStringToObject(any(), any());
        assertThat(e.getMessage()).isEqualTo(errorMessage);
    }

    @Test
    void givenA4xxResponse_whenParseToObject_thenARequestIsNotValidWithProperMessageIsThrown() {
        String errorMessage = "Request is not valid";
        ResponseEntity<String> apiResponse = apiResponse(HttpStatus.BAD_REQUEST, errorMessage);
        when(jsonParser.parseStringToObject(eq(apiResponse.getBody()), any(Class.class)))
                .thenReturn(apiErrorResponse(errorMessage));

        Exception e = assertThrows(RequestIsNotValid.class, () -> responseHandler.parseToObject(apiResponse, Object.class));

        verify(jsonParser, times(1)).parseStringToObject(any(), any());
        assertThat(e.getMessage()).isEqualTo(errorMessage);
    }

    @Test
    void givenWhateverNon2xxNeither4xxResponse_whenParseToObject_thenAServerErrorWithProperMessageIsThrown() {
        String errorMessage = "Some server error";
        ResponseEntity<String> apiResponse = apiResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        when(jsonParser.parseStringToObject(eq(apiResponse.getBody()), any(Class.class)))
                .thenReturn(apiErrorResponse(errorMessage));

        Exception e = assertThrows(ServerError.class, () -> responseHandler.parseToObject(apiResponse, Object.class));

        verify(jsonParser, times(1)).parseStringToObject(any(), any());
        assertThat(e.getMessage()).isEqualTo(errorMessage);
    }

    @Test
    void givenNotFoundResponseWithoutBody_whenParseToObject_thenAnElementDoesNotExistWithUnknownErrorMessageIsThrown() {
        ResponseEntity<String> apiResponse = apiResponseWithoutBody(HttpStatus.NOT_FOUND);

        Exception e = assertThrows(ElementDoesNotExist.class, () -> responseHandler.parseToObject(apiResponse, Object.class));

        verify(jsonParser, times(0)).parseStringToObject(any(), any());
        assertThat(e.getMessage()).isEqualTo("Unknown error");
    }

    @Test
    void givenWhateverNonSuccessfulResponseWithoutBody_whenParseToObject_thenAnUnknownErrorIsThrown() {
        ResponseEntity<String> apiResponse = apiResponseWithoutBody(HttpStatus.BAD_REQUEST);

        Exception e = assertThrows(ServerError.class, () -> responseHandler.parseToObject(apiResponse, Object.class));

        verify(jsonParser, times(0)).parseStringToObject(any(), any());
        assertThat(e.getMessage()).isEqualTo("Unknown error");
    }

    @Test
    void givenNotFoundResponse_whenAJsonParsingErrorOccurs_thenAnUnexpectedErrorIsThrown() {
        ResponseEntity<String> apiResponse = apiResponse(HttpStatus.NOT_FOUND, "not valid json");

        // Real json parser
        ApiResponseHandler responseHandler = new ApiResponseHandler(new JsonParser(new ObjectMapper()));
        Exception e = assertThrows(ElementDoesNotExist.class, () -> responseHandler.parseToObject(apiResponse, Object.class));

        assertThat(e.getMessage()).isEqualTo("Error parsing JSON string");
    }

    @Test
    void givenWhateverNonSuccessfulResponse_whenAJsonParsingErrorOccurs_thenAnUnexpectedErrorIsThrown() {
        ResponseEntity<String> apiResponse = apiResponse(HttpStatus.BAD_REQUEST, "not valid json");

        // Real json parser
        ApiResponseHandler responseHandler = new ApiResponseHandler(new JsonParser(new ObjectMapper()));
        Exception e = assertThrows(UnexpectedError.class, () -> responseHandler.parseToObject(apiResponse, Object.class));

        assertThat(e.getMessage()).isEqualTo("Error parsing JSON string");
    }

    private ApiErrorResponse apiErrorResponse(String errorMessage) {
        return new ApiErrorResponse(errorMessage);
    }

    private ResponseEntity apiResponseWithoutBody(HttpStatus status) {
        return new ResponseEntity(status);
    }

    private <T> ResponseEntity<T> apiResponse(HttpStatus status, T body) {
        return new ResponseEntity<>(body, status);
    }
}