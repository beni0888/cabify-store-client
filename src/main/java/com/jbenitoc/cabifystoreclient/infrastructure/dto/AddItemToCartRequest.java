package com.jbenitoc.cabifystoreclient.infrastructure.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddItemToCartRequest {
    public final String itemCode;
    public final int quantity;
}
