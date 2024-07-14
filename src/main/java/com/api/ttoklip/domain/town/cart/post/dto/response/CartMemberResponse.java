package com.api.ttoklip.domain.town.cart.post.dto.response;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.privacy.domain.Interest;
import com.api.ttoklip.domain.privacy.dto.InterestResponse;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartMemberResponse {
    private String nickname;
    private String profileImgUrl;
    private String email;
    private List<InterestResponse> interests;


    @Builder
    private CartMemberResponse(final String nickname, final String profileImgUrl, final String email,
                               final List<InterestResponse> interests) {
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.email = email;
        this.interests = interests;
    }

    public static CartMemberResponse of(final Member member, final List<Interest> interests) {

        List<InterestResponse> interestResponses = interests.stream()
                .map(InterestResponse::from)
                .toList();

        return CartMemberResponse.builder()
                .nickname(member.getNickname())
                .profileImgUrl(member.getProfile().getProfileImgUrl())
                .email(member.getEmail())
                .interests(interestResponses)
                .build();
    }
}
