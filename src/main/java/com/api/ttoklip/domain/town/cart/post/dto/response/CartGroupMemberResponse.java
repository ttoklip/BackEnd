package com.api.ttoklip.domain.town.cart.post.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CartGroupMemberResponse {
    private List<CartMemberResponse> cartMemberResponses;

    @Builder
    private CartGroupMemberResponse(final List<CartMemberResponse> cartMemberResponses) {
        this.cartMemberResponses = cartMemberResponses;
    }

    public static CartGroupMemberResponse of(final List<CartMemberResponse> cartMemberResponses) {
        return CartGroupMemberResponse.builder()
                .cartMemberResponses(cartMemberResponses)
                .build();
    }
}
