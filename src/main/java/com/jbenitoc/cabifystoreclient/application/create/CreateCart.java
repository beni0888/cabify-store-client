package com.jbenitoc.cabifystoreclient.application.create;

import com.jbenitoc.cabifystoreclient.domain.model.Cart;
import com.jbenitoc.cabifystoreclient.domain.model.StoreService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateCart {

    private StoreService storeService;

    public Cart execute() {
        return storeService.createCart();
    }
}
