package com.jbenitoc.cabifystoreclient.application.add;

import com.jbenitoc.cabifystoreclient.domain.model.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

class AddItemToCartTest {
    private StoreService storeService;
    private AddItemToCart addItemToCart;

    @BeforeEach
    void setUp() {
        storeService = mock(StoreService.class);
        addItemToCart = new AddItemToCart(storeService);
    }

    @Test
    void whenAddItemToCart_thenItWorks() {
        String cartId = randomUUID().toString();
        String itemCode = "VOUCHER";
        int quantity = 1;
        AddItemToCartCommand command = new AddItemToCartCommand(cartId, itemCode, quantity);

        addItemToCart.execute(command);

        verify(storeService, times(1)).addItemToCart(cartId, itemCode, quantity);
    }
}