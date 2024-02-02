package com.api.ttoklip.domain.auth.dto;

import com.api.ttoklip.domain.user.domain.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
public class AuthRes {

    @Schema(type = "string", example = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2NTI3OTgxOTh9.6CoxHB_siOuz6PxsxHYQCgUT1_QbdyKTUwStQDutEd1-cIIARbQ0cyrnAmpIgi3IBoLRaqK7N1vXO42nYy4g5g", description = "access token 을 출력합니다.")
    private String accessToken;

//    @Schema( type = "string", example = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2NTI3OTgxOTh9.asdf8as4df865as4dfasdf65_asdfweioufsdoiuf_432jdsaFEWFSDV_sadf" , description="refresh token 을 출력합니다.")
//    private String refreshToken;

    @Schema( type = "string", example ="Bearer", description="권한(Authorization) 값 해더의 명칭을 지정합니다.")
    private String tokenType = "Bearer";

    @Schema( type = "Role", example = "USER", description = "Role을 출력합니다.")
    private Role role;


    @Builder
    public AuthRes(String accessToken, Role role) {
        this.accessToken = accessToken;
        this.role = role;
    }
}
