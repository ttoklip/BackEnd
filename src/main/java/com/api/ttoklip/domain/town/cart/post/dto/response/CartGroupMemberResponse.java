package com.api.ttoklip.domain.town.cart.post.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartGroupMemberResponse {
    private final List<CartMemberResponse> cartMemberResponses;

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
