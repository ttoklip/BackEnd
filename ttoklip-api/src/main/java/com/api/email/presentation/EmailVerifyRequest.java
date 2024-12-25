package com.api.email.presentation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record EmailVerifyRequest(
        @Schema(description = "이메일", example = "ttok123@naver.com")
        @NotBlank(message = "email은 필수입니다.")
        String email,

        @Schema(description = "인증코드", example = "280502")
        @NotBlank(message = "인증코드는 필수입니다.")
        String verifyCode
) {
}
