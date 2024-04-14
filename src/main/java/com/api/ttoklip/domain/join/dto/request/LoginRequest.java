package com.api.ttoklip.domain.join.dto.request;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Schema(description = "직접 로그인 ID", example = "ttok123")
    private String joinId;

    @Schema(description = "비밀번호", example = "asdf1234!")
    private String password;
}
