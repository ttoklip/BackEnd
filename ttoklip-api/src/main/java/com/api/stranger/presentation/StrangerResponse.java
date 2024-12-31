package com.api.stranger.presentation;

import com.domain.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;

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
        return new StrangerResponse(
                member.getStreet(),
                member.getNickname(),
                member.getId(),
                member.getProfile().getProfileImgUrl(),
                member.getIndependentYear(),
                member.getIndependentMonth()
        );
    }
}
