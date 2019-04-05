package com.jbenitoc.cabifystoreclient.infrastructure;

import com.jbenitoc.cabifystoreclient.domain.model.Cart;
import com.jbenitoc.cabifystoreclient.domain.model.Price;
import com.jbenitoc.cabifystoreclient.domain.model.StoreService;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.AddItemToCartRequest;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.CreateCartResponse;
import com.jbenitoc.cabifystoreclient.infrastructure.dto.GetCartTotalAmountResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@AllArgsConstructor
@Service
public class RestStoreService implements StoreService {

    private RestTemplate restTemplate;

    @Override
    public Cart createCart() {
        CreateCartResponse response = restTemplate.postForObject("http://localhost:8080/cart", null, CreateCartResponse.class);
        return new Cart(response.id);
    }

    @Override
    public void addItemToCart(@NonNull String cartId, @NonNull String itemCode, int quantity) {
        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/cart/").pathSegment(cartId)
                .pathSegment("item").toUriString();
        AddItemToCartRequest request = new AddItemToCartRequest(itemCode, quantity);
        restTemplate.postForEntity(url, request, Void.class);
    }

    @Override
    public Price getTotalAmount(@NonNull String cartId) {
        String url = "http://localhost:8080/cart/{cartId}";
        GetCartTotalAmountResponse response = restTemplate.getForObject(url, GetCartTotalAmountResponse.class, cartId);
        return new Price(response.totalAmount);
    }

    @Override
    public void deleteCart(String cartId) {
        String url = "http://localhost:8080/cart/{cartId}";
        restTemplate.delete(url, cartId);
    }
}
