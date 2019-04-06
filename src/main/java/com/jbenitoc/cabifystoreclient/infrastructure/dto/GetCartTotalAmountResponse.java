package com.jbenitoc.cabifystoreclient.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class GetCartTotalAmountResponse {
    public final String cartId;
    public final String totalAmount;

    @JsonCreator
    public GetCartTotalAmountResponse(@JsonProperty("cartId") String cartId, @JsonProperty("totalAmount") String totalAmount) {
        this.cartId = cartId;
        this.totalAmount = totalAmount;
    }
}
