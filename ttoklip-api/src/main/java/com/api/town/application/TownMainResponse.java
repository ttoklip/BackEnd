package com.api.town.application;

import com.domain.cart.application.CartThumbnailResponse;
import com.domain.community.application.CommunityRecent3Response;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TownMainResponse {
    private List<CommunityRecent3Response> communityRecent3;
    private List<CartThumbnailResponse> cartRecent3;
    private String street;
}
