package com.api.ttoklip.domain.town.community.post.dto.response;

import com.api.ttoklip.domain.mypage.main.dto.response.UserCartSingleResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CartMainResponse {
    private List<CommunityRecent3Response> communityRecent3;
    private List<UserCartSingleResponse> cartRecent3;
    private String street;
}
