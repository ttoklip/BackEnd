package com.api.town.application;

import com.domain.cart.application.CartThumbnailResponse;
import com.domain.community.application.CommunityRecent3Response;
import java.util.List;

public record TownMainResponse(
        List<CommunityRecent3Response> communityRecent3,
        List<CartThumbnailResponse> cartRecent3,
        String street
) {
    public static TownMainResponse of(
            List<CommunityRecent3Response> communityRecent3,
            List<CartThumbnailResponse> cartRecent3,
            String street
    ) {
        return new TownMainResponse(communityRecent3, cartRecent3, street);
    }
}
