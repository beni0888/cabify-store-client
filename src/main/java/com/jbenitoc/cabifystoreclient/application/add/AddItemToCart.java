package com.jbenitoc.cabifystoreclient.application.add;

import com.jbenitoc.cabifystoreclient.domain.model.StoreService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddItemToCart {

    private StoreService storeService;

    public void execute(AddItemToCartCommand command) {
        storeService.addItemToCart(command.getCartId(), command.getItemCode(), command.getQuantity());
    }
}
