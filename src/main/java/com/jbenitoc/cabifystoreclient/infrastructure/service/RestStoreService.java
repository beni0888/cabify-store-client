package com.jbenitoc.cabifystoreclient.infrastructure.service;

import com.jbenitoc.cabifystoreclient.domain.model.Cart;
import com.jbenitoc.cabifystoreclient.domain.model.Price;
import com.jbenitoc.cabifystoreclient.domain.model.StoreService;
import com.jbenitoc.cabifystoreclient.infrastructure.configuration.StoreApiConfiguration;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.AddItemToCartRequest;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.CreateCartResponse;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.GetCartTotalAmountResponse;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class RestStoreService implements StoreService {

    private RestTemplate restTemplate;
    private StoreApiConfiguration configuration;
    private Map<String, String> urlMap = new HashMap<>();

    public RestStoreService(RestTemplate restTemplate, StoreApiConfiguration configuration) {
        this.restTemplate = restTemplate;
        this.configuration = configuration;
    }

    private String composeUrl(String path) {
        return urlMap.computeIfAbsent(path, p -> configuration.getBaseUrl() + p);
    }

    @Override
    public Cart createCart() {
        String url = composeUrl(configuration.getEndpoint().getCreateCart());
        CreateCartResponse response = restTemplate.postForObject(url, null, CreateCartResponse.class);
        return new Cart(response.id);
    }

    @Override
    public void addItemToCart(@NonNull String cartId, @NonNull String itemCode, int quantity) {
        AddItemToCartRequest request = new AddItemToCartRequest(itemCode, quantity);
        String url = composeUrl(configuration.getEndpoint().getAddItem());
        restTemplate.postForEntity(url, request, Void.class, cartId);
    }

    @Override
    public Price getTotalAmount(@NonNull String cartId) {
        String url = composeUrl(configuration.getEndpoint().getGetTotal());
        GetCartTotalAmountResponse response = restTemplate.getForObject(url, GetCartTotalAmountResponse.class, cartId);
        return new Price(response.totalAmount);
    }

    @Override
    public void deleteCart(String cartId) {
        String url = composeUrl(configuration.getEndpoint().getDeleteCart());
        restTemplate.delete(url, cartId);
    }
}
