package com.jbenitoc.cabifystoreclient.application.delete;

import com.jbenitoc.cabifystoreclient.domain.model.StoreService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteCart {

    private StoreService storeService;

    public void execute(DeleteCartQuery query) {
        storeService.deleteCart(query.getCartId());
    }
}
