package com.domain.profile.application.response;

import com.domain.interest.application.response.InterestResponse;
import com.domain.member.domain.Member;
import java.util.List;

public record TargetMemberProfile(
        Long targetMemberId,
        String profileImgUrl,
        String email,
        String nickname,
        String street,
        int independentYear,
        int independentMonth,
        int likeCount,
        List<InterestResponse> interests
) {

    public static TargetMemberProfile of(Member member, int likeCount, List<InterestResponse> interests) {
        return new TargetMemberProfile(
                member.getId(), member.getProfile().getProfileImgUrl(), member.getEmail(), member.getNickname(),
                member.getStreet(), member.getIndependentYear(), member.getIndependentMonth(), likeCount, interests
        );
    }
}
