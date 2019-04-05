package com.jbenitoc.cabifystoreclient.infrastructure.configuration;

import com.jbenitoc.cabifystoreclient.application.add.AddItemToCart;
import com.jbenitoc.cabifystoreclient.application.create.CreateCart;
import com.jbenitoc.cabifystoreclient.application.delete.DeleteCart;
import com.jbenitoc.cabifystoreclient.application.total.GetCartTotalAmount;
import com.jbenitoc.cabifystoreclient.domain.model.StoreService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextConfiguration {

    @Bean
    public CreateCart createCart(StoreService storeService) {
        return new CreateCart(storeService);
    }

    @Bean
    public AddItemToCart addItemToCart(StoreService storeService) {
        return new AddItemToCart(storeService);
    }

    @Bean
    GetCartTotalAmount getCartTotalAmount(StoreService storeService) {
        return new GetCartTotalAmount(storeService);
    }

    @Bean
    DeleteCart deleteCart(StoreService storeService) {
        return new DeleteCart(storeService);
    }
}
