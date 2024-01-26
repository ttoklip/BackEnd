package com.api.ttoklip.domain.town.cart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CartSummaryResponse {

    private Long id;
    private String title;
    private String location;
    private int totalPrice;
    private int party;
    private int commentCount;
}
