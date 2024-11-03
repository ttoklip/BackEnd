package com.api.ttoklip.domain.profile;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.privacy.dto.InterestResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record TargetMemberProfile(

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

    public static TargetMemberProfile of(Member member, int likeCount, List<InterestResponse> interests) {
        return new TargetMemberProfile(
                member.getId(), member.getProfile().getProfileImgUrl(), member.getEmail(), member.getNickname(),
                member.getStreet(), member.getIndependentYear(), member.getIndependentMonth(), likeCount, interests
        );
    }
}
