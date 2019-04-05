package com.jbenitoc.cabifystoreclient.application.add;

import lombok.Getter;
import lombok.NonNull;

@Getter
public final class AddItemToCartCommand {
    private final String cartId;
    private final String itemCode;
    private final int quantity;

    public AddItemToCartCommand(@NonNull String cartId, @NonNull String itemCode, @NonNull int quantity) {
        this.cartId = cartId;
        this.itemCode = itemCode;
        this.quantity = quantity;
    }
}
