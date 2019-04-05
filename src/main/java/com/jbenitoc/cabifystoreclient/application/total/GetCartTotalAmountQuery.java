package com.jbenitoc.cabifystoreclient.application.total;

import lombok.NonNull;

public final class GetCartTotalAmountQuery {
    public final String cartId;

    public GetCartTotalAmountQuery(@NonNull String cartId) {
        this.cartId = cartId;
    }
}
