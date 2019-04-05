package com.jbenitoc.cabifystoreclient.application.total;

import com.jbenitoc.cabifystoreclient.domain.model.StoreService;
import com.jbenitoc.cabifystoreclient.domain.model.Price;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetCartTotalAmount {

    private StoreService storeService;

    public Price execute(GetCartTotalAmountQuery query) {
        return storeService.getTotalAmount(query.cartId);
    }
}
