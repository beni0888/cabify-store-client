package com.jbenitoc.cabifystoreclient.domain.model;

public interface StoreService {

    Cart createCart();

    void addItemToCart(String cartId, String itemCode, int quantity);

    Price getTotalAmount(String cartId);

    void deleteCart(String cartId);
}
