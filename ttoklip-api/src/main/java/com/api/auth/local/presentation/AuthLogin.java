package com.api.auth.local.presentation;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthLogin(
        @Schema(description = "직접 로그인 ID", example = "ttok123@naver.com")
        String email,

        @Schema(description = "비밀번호", example = "asdf1234!")
        String password
) {

}
