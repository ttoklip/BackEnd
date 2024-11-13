package com.api.email.presentation;

import io.swagger.v3.oas.annotations.media.Schema;

public record EmailVerifyRequest(
        @Schema(description = "이메일", example = "ttok123@naver.com") String email,
        @Schema(description = "인증코드", example = "280502") String verifyCode
) {
}
