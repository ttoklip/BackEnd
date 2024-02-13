package com.api.ttoklip.domain.mypage.main.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MyPageResponse {
    @Schema(description = "유저의 닉네임")
    private String userNickname;
    @Schema(description = "유저의 동네")
    private String userArea;
   /* @Schema(description = "유저의 레벨 이미지")
    private List<ImageResponse> imageUrls;*/
    @Schema(description = "유저의 경험치 충족도")
    private Long levelExp;
    @Schema(description = "유저의 레벨(ex:새싹)")
    private String userLevel;
}
