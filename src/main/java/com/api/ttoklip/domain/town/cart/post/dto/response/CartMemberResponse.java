package com.api.ttoklip.domain.town.cart.post.dto.response;

import com.api.ttoklip.domain.privacy.domain.Interest;
import com.api.ttoklip.domain.privacy.dto.InterestResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CartMemberResponse {
    private String nickname;
    private String profileImgUrl;
    private String email;
    private List<InterestResponse> interests;
}
