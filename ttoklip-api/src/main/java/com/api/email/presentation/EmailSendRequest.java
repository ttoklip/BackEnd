package com.api.email.presentation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record EmailSendRequest(
        @Schema(description = "이메일", example = "ttok123@naver.com")
        @NotBlank(message = "email은 필수입니다.")
        String email
) {
}
