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

    @Schema( type = "string", example = "string@aa.bb", description="계정 이메일 입니다.")
    @Email
    private String email;
}
