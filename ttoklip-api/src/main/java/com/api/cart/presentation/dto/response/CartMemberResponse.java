package com.api.cart.presentation.dto.response;

import com.domain.interest.application.response.InterestResponse;
import com.domain.interest.domain.Interest;
import com.domain.member.domain.Member;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartMemberResponse {
    private final String nickname;
    private final String profileImgUrl;
    private final String email;
    private final List<InterestResponse> interests;


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
