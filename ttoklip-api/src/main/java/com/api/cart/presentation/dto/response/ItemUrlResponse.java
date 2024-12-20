package com.api.cart.presentation.dto.response;

import com.domain.cart.domain.ItemUrl;
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
