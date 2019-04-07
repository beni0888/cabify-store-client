package com.jbenitoc.cabifystoreclient.application.create;

import com.jbenitoc.cabifystoreclient.domain.model.Cart;
import com.jbenitoc.cabifystoreclient.domain.model.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreateCartTest {

    private StoreService storeService;
    private CreateCart createCart;

    @BeforeEach
    void setUp() {
        storeService = mock(StoreService.class);
        createCart = new CreateCart(storeService);
    }

    @Test
    void whenCreateCart_thenItWorks() {
        Cart expectedCart = new Cart(randomUUID().toString());

        when(storeService.createCart()).thenReturn(expectedCart);

        Cart cart = createCart.execute();

        assertThat(cart).isEqualTo(expectedCart);
    }
}