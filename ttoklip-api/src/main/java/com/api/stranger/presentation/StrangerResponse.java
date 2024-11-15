package com.api.stranger.presentation;

import com.domain.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record StrangerResponse(
        @Schema(description = "유저의 동네")
        String street,

        @Schema(description = "유저의 닉네임")
        String nickname,

        @Schema(description = "유저의 아이디")
        Long userId,

        @Schema(description = "유저의 레벨 이미지")
        String profileImage,

        int independentYear,

        int independentMonth
) {
    public static StrangerResponse of(final Member member) {

        return builder()
                .nickname(member.getNickname())
                .street(member.getStreet())
                .userId(member.getId())
                .profileImage(member.getProfile().getProfileImgUrl())
                .independentMonth(member.getIndependentMonth())
                .independentYear(member.getIndependentYear())
                .build();
    }
}
