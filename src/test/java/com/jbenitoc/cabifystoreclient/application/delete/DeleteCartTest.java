package com.jbenitoc.cabifystoreclient.application.delete;

import com.jbenitoc.cabifystoreclient.domain.model.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

class DeleteCartTest {


    private StoreService storeService;
    private DeleteCart deleteCart;

    @BeforeEach
    void setUp() {
        storeService = mock(StoreService.class);
        deleteCart = new DeleteCart(storeService);
    }

    @Test
    void whenDeleteCart_thenItWorks() {
        String cartId = randomUUID().toString();
        DeleteCartQuery query = new DeleteCartQuery(cartId);

        deleteCart.execute(query);

        verify(storeService, times(1)).deleteCart(cartId);
    }
}