package com.api.ttoklip.domain.join.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Schema(description = "직접 로그인 ID", example = "ttok123@naver.com")
    private String email;

    @Schema(description = "비밀번호", example = "asdf1234!")
    private String password;


}
