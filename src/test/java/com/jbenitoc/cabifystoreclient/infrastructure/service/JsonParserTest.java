package com.jbenitoc.cabifystoreclient.infrastructure.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.ApiErrorResponse;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.CreateCartResponse;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.GetCartTotalAmountResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonParserTest {

    private JsonParser jsonParser = new JsonParser(new ObjectMapper());

    @Test
    void givenCreateCartResponse_whenItIsUnmarshalled_thenItWorks() throws IOException {
        String jsonString = "{\"id\":\"56c7b5f7-115b-4cb9-9658-acb7b849d5d5\"}";
        CreateCartResponse response = jsonParser.parseStringToObject(jsonString, CreateCartResponse.class);
        assertThat(response.id).isEqualTo("56c7b5f7-115b-4cb9-9658-acb7b849d5d5");
    }

    @Test
    void givenGetCartTotalAmountResponse_whenItIsUnmarshalled_thenItWorks() throws IOException {
        String jsonString = "{\"cartId\":\"38049d83-74bb-4cd9-af5e-70f2d1b37f5a\",\"totalAmount\":5.0}";
        GetCartTotalAmountResponse response = jsonParser.parseStringToObject(jsonString, GetCartTotalAmountResponse.class);
        assertThat(response.cartId).isEqualTo("38049d83-74bb-4cd9-af5e-70f2d1b37f5a");
        assertThat(response.totalAmount).isEqualTo("5.0");
    }

    @Test
    void givenApiErrorResponse_whenItIsUnmarshalled_thenItWorks() throws IOException {
        String jsonString = "{\"status\":400,\"message\":\"Cart ID [1] is not valid, it should be a valid UUID\"}";
        ApiErrorResponse response = jsonParser.parseStringToObject(jsonString, ApiErrorResponse.class);
        assertThat(response.message).isEqualTo("Cart ID [1] is not valid, it should be a valid UUID");
    }

    @Test
    void givenApiErrorResponseWithUnknownFields_whenItIsUnmarshalled_thenItWorks() throws IOException {
        String jsonString = "{\"status\":400,\"message\":\"Cart ID [1] is not valid, it should be a valid UUID\", \"foo\":\"bar\"}";
        ApiErrorResponse response = jsonParser.parseStringToObject(jsonString, ApiErrorResponse.class);
        assertThat(response.message).isEqualTo("Cart ID [1] is not valid, it should be a valid UUID");
    }

}
