package com.api.ttoklip.domain.member.dto.response;

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

    public static TargetMemberProfile of(Long targetMemberId, String nickname, String profileImgUrl, String street,
                                         int independentYear, int independentMonth, int likeCount) {
        return new TargetMemberProfile(
                targetMemberId, nickname, profileImgUrl,
                street, independentYear, independentMonth, likeCount
        );
    }
}
