package com.api.ttoklip.domain.town.cart.controller.dto.response;

import com.api.ttoklip.domain.town.cart.domain.CartImage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CartImageResponse {

    private String imageUrl;

    public static CartImageResponse from(final CartImage image) {
        return CartImageResponse.builder()
                .imageUrl(image.getUrl())
                .build();
    }
}
