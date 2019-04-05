package com.jbenitoc.cabifystoreclient.application.delete;

import lombok.Getter;
import lombok.NonNull;

@Getter
public final class DeleteCartQuery {
    private final String cartId;

    public DeleteCartQuery(@NonNull String cartId) {
        this.cartId = cartId;
    }
}
