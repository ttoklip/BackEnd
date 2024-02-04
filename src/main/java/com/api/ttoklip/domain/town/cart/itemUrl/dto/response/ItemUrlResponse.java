package com.api.ttoklip.domain.town.cart.itemUrl.dto.response;

import com.api.ttoklip.domain.town.cart.image.dto.response.CartImageResponse;
import com.api.ttoklip.domain.town.cart.image.entity.CartImage;
import com.api.ttoklip.domain.town.cart.itemUrl.entity.ItemUrl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemUrlResponse {

    private String itemUrl;

    public static ItemUrlResponse from(final ItemUrl url) {
        return ItemUrlResponse.builder()
                .itemUrl(url.getUrl())
                .build();
    }
}
