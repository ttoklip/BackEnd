package com.api.ttoklip.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignInReq {

    @Schema( type = "string", example = "string@aa.bb", description="계정 이메일 입니다.")
    @Email
    private String email;

    @Schema( type = "string", example = "string", description="providerId 입니다.")
    @NotBlank(message = "providerId를 입력해야합니다.")
    private String providerId;
}
