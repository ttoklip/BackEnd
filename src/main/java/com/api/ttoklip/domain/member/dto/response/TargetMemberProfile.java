package com.api.ttoklip.domain.member.dto.response;

import com.api.ttoklip.domain.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;

public record TargetMemberProfile(

        @Schema(description = "타겟 멤버의 ID")
        Long targetMemberId,

        @Schema(description = "타겟 멤버의 프로필 이미지 url")
        String profileImgUrl,

        @Schema(description = "타겟 멤버의 닉네임")
        String nickname,

        @Schema(description = "타겟 멤버의 주소")
        String street,

        @Schema(description = "타겟 멤버의 독립 경력 년수")
        int independentYear,

        @Schema(description = "타겟 멤버의 독립 경력 개월 수")
        int independentMonth,

        @Schema(description = "타겟 멤버가 받은 좋아요 수")
        int likeCount
) {

    public static TargetMemberProfile of(Member member, int likeCount) {
        return new TargetMemberProfile(
                member.getId(), member.getProfile().getProfileImgUrl(), member.getNickname(),
                member.getStreet(), member.getIndependentYear(), member.getIndependentMonth(), likeCount
        );
    }
}
