package com.jbenitoc.cabifystoreclient.infrastructure.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbenitoc.cabifystoreclient.domain.model.*;
import com.jbenitoc.cabifystoreclient.infrastructure.configuration.StoreApiConfiguration;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.AddItemToCartRequest;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.ApiErrorResponse;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.CreateCartResponse;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.GetCartTotalAmountResponse;
import lombok.NonNull;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class RestStoreService implements StoreService {

    private RestTemplate restTemplate;
    private ObjectMapper mapper;
    private StoreApiConfiguration configuration;
    private Map<String, String> urlMap = new HashMap<>();

    public RestStoreService(RestTemplate restTemplate, ObjectMapper mapper, StoreApiConfiguration configuration) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.configuration = configuration;
    }

    private String composeUrl(String path) {
        return urlMap.computeIfAbsent(path, p -> configuration.getBaseUrl() + p);
    }

    private <T> T parseApiResponse(ResponseEntity<String> response, Class<T> targetClass) {
        if (response.getStatusCode().is2xxSuccessful()) {
            if (response.getBody() == null) {
                return null;
            }

            return parseStringToObject(response.getBody(), targetClass);
        }

        ApiErrorResponse errorResponse = parseStringToObject(response.getBody(), ApiErrorResponse.class);
        if(response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new ElementDoesNotExist(errorResponse.message);
        }
        if(response.getStatusCode().is4xxClientError()) {
            throw new RequestIsNotValid(errorResponse.message);
        }
        throw new ServerError(errorResponse.message);
    }

    private <T> T parseStringToObject(String jsonString, Class<T> targetClass) {
        try {
            return mapper.readValue(jsonString, targetClass);
        } catch (IOException e) {
            throw new UnexpectedError("Error parsing JSON string", e);
        }
    }

    @Override
    public Cart createCart() {
        String url = composeUrl(configuration.getEndpoint().getCreateCart());
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        CreateCartResponse createCartResponse = parseApiResponse(response, CreateCartResponse.class);
        return new Cart(createCartResponse.id);
    }

    @Override
    public void addItemToCart(@NonNull String cartId, @NonNull String itemCode, int quantity) {
        AddItemToCartRequest request = new AddItemToCartRequest(itemCode, quantity);
        String url = composeUrl(configuration.getEndpoint().getAddItem());
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class, cartId);
        parseApiResponse(response, Void.class);
    }

    @Override
    public Price getTotalAmount(@NonNull String cartId) {
        String url = composeUrl(configuration.getEndpoint().getGetTotal());
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, cartId);
        GetCartTotalAmountResponse totalAmountResponse = parseApiResponse(response, GetCartTotalAmountResponse.class);
        return new Price(totalAmountResponse.totalAmount);
    }

    @Override
    public void deleteCart(String cartId) {
        String url = composeUrl(configuration.getEndpoint().getDeleteCart());
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class, cartId);
        parseApiResponse(response, Void.class);
    }
}
