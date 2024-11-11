package com.api.profile.presentation;

import com.domain.interest.application.response.InterestResponse;
import com.domain.profile.application.response.TargetMemberProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record TargetMemberProfileResponse(

        @Schema(description = "타겟 멤버의 ID")
        Long targetMemberId,

        @Schema(description = "타겟 멤버의 프로필 이미지 url")
        String profileImgUrl,

        @Schema(description = "타겟 멤버의 이메일")
        String email,

        @Schema(description = "타겟 멤버의 닉네임")
        String nickname,

        @Schema(description = "타겟 멤버의 주소")
        String street,

        @Schema(description = "타겟 멤버의 독립 경력 년수")
        int independentYear,

        @Schema(description = "타겟 멤버의 독립 경력 개월 수")
        int independentMonth,

        @Schema(description = "타겟 멤버가 받은 좋아요 수")
        int likeCount,

        @Schema(description = "타겟 멤버의 관심사 리스트")
        List<InterestResponse> interests
) {

    public static TargetMemberProfileResponse from(final TargetMemberProfile target) {
        return new TargetMemberProfileResponse(
                target.targetMemberId(), target.profileImgUrl(), target.email(), target.nickname(), target.street(),
                target.independentYear(), target.independentMonth(), target.likeCount(), target.interests()
        );
    }
}
