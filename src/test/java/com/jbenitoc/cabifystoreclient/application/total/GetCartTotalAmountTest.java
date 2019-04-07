package com.jbenitoc.cabifystoreclient.application.total;

import com.jbenitoc.cabifystoreclient.domain.model.Price;
import com.jbenitoc.cabifystoreclient.domain.model.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GetCartTotalAmountTest {

    private StoreService storeService;
    private GetCartTotalAmount getCartTotalAmount;

    @BeforeEach
    void setUp() {
        storeService = mock(StoreService.class);
        getCartTotalAmount = new GetCartTotalAmount(storeService);
    }

    @Test
    void whenGetCartTotalAmount_thenItWorks() {
        String cartId = randomUUID().toString();
        GetCartTotalAmountQuery query = new GetCartTotalAmountQuery(cartId);
        Price expectedPrice = new Price("10.50");

        when(storeService.getTotalAmount(cartId)).thenReturn(expectedPrice);

        Price price = getCartTotalAmount.execute(query);

        assertThat(price).isEqualTo(expectedPrice);
    }
}