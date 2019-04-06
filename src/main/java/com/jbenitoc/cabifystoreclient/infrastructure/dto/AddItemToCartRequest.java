package com.jbenitoc.cabifystoreclient.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class AddItemToCartRequest {
    public final String itemCode;
    public final int quantity;
}
