package com.api.email.presentation;

import io.swagger.v3.oas.annotations.media.Schema;

public record EmailSendRequest(
        @Schema(description = "이메일", example = "ttok123@naver.com") String email
) {
}
