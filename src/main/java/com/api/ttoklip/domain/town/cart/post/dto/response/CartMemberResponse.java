package com.api.ttoklip.domain.town.cart.post.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CartMemberResponse {
    private String nickname;
    private String profileImgUrl;
}
