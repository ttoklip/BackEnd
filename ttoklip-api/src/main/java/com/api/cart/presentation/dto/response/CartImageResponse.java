package com.api.cart.presentation.dto.response;

import com.domain.cart.domain.CartImage;
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
