package com.api.ttoklip.global.security.oauth2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OAuthLoginRequest {

    @Schema(type = "string", description = "oauth accessToken", example = "AAAAux5O0y30x7G1twup/hPQIdsu/B3i3WL490lghVyU=")
    private String accessToken;

    @Schema(type = "string", description = "provider", example = "kakao or naver or local")
    private String provider;
}
