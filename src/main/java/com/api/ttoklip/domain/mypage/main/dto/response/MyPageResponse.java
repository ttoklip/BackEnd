package com.api.ttoklip.domain.mypage.main.dto.response;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.question.image.dto.response.ImageResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MyPageResponse {
    @Schema(description = "유저의 동네")
    private String street;
    @Schema(description = "유저의 닉네임")
    private String nickname;

    @Schema(description = "유저의 레벨 이미지")
    private String profileImage;

    private final int independentYear;
    private final int independentMonth;

    public static MyPageResponse of(final Member member) {
        System.out.println("member.getProfile() = " + member.getProfile());

        return MyPageResponse.builder()
                .nickname(member.getNickname())
                .street(member.getStreet())
                .profileImage(member.getProfile().getProfileImgUrl())
                .independentMonth(member.getIndependentMonth())
                .independentYear(member.getIndependentYear())
                .build();
    }
}
