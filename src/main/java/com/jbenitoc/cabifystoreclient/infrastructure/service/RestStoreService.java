package com.jbenitoc.cabifystoreclient.infrastructure.service;

import com.jbenitoc.cabifystoreclient.domain.model.*;
import com.jbenitoc.cabifystoreclient.infrastructure.configuration.StoreApiConfiguration;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.AddItemToCartRequest;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.CreateCartResponse;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.GetCartTotalAmountResponse;
import lombok.NonNull;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class RestStoreService implements StoreService {

    private RestTemplate restTemplate;
    private StoreApiConfiguration configuration;
    private ApiResponseHandler apiResponseHandler;
    private Map<String, String> urlMap = new HashMap<>();

    public RestStoreService(RestTemplate restTemplate, StoreApiConfiguration configuration, ApiResponseHandler apiResponseHandler) {
        this.restTemplate = restTemplate;
        this.configuration = configuration;
        this.apiResponseHandler = apiResponseHandler;
    }

    private String composeUrl(String path) {
        return urlMap.computeIfAbsent(path, p -> configuration.getBaseUrl() + p);
    }

    @Override
    public Cart createCart() {
        String url = composeUrl(configuration.getEndpoint().getCreateCart());
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        CreateCartResponse createCartResponse = apiResponseHandler.parseApiResponse(response, CreateCartResponse.class);
        return new Cart(createCartResponse.id);
    }

    @Override
    public void addItemToCart(@NonNull String cartId, @NonNull String itemCode, int quantity) {
        AddItemToCartRequest request = new AddItemToCartRequest(itemCode, quantity);
        String url = composeUrl(configuration.getEndpoint().getAddItem());
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class, cartId);
        apiResponseHandler.parseApiResponse(response, Void.class);
    }

    @Override
    public Price getTotalAmount(@NonNull String cartId) {
        String url = composeUrl(configuration.getEndpoint().getGetTotal());
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, cartId);
        GetCartTotalAmountResponse totalAmountResponse = apiResponseHandler.parseApiResponse(response, GetCartTotalAmountResponse.class);
        return new Price(totalAmountResponse.totalAmount);
    }

    @Override
    public void deleteCart(String cartId) {
        String url = composeUrl(configuration.getEndpoint().getDeleteCart());
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class, cartId);
        apiResponseHandler.parseApiResponse(response, Void.class);
    }
}
