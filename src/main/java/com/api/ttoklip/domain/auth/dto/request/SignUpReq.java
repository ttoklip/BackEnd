package com.api.ttoklip.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpReq {

    @Schema( type = "string", example = "123123", description="카카오 고유 유저 ID 입니다.")
    private String providerId;

    @Schema( type = "string", example = "홍길동", description="이름")
    @Size(min = 2, message = "2자 이상 입력해주세요")
    private String name;

    @Schema( type = "string", example = "string", description="닉네임 입니다.")
    @NotBlank(message = "닉네임을 입력해야 합니다.")
    @Pattern(regexp = "^(?=.*[가-힣a-zA-Z0-9])[가-힣a-zA-Z0-9]{2,10}", message = "한글, 영어, 숫자 가능, 2~10자, 특수기호 불가")
    private String nickname;

    @Schema(type = "string", example = "http://k.kakaocdn.net/dn/1G9kp/btsAot8liOn/8CWudi3uy07rvFNUkk3ER0/img_640x640.jpg", description = "프로필 이미지 입니다.")
    private String imageUrl;

    // Todo 우리동네 설정, 나의 독립경력 설정, 고민거리 선택
}
