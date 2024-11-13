package com.api.auth.oauth2.presentation;

import com.domain.member.domain.vo.Provider;
import io.swagger.v3.oas.annotations.media.Schema;

public record OAuthLogin(
        @Schema(type = "string", description = "oauth accessToken", example = "AAAAux5O0y30x7G1twup/hPQIdsu/B3i3WL490lghVyU=")
        String accessToken,

        @Schema(type = "string", description = "provider", example = "kakao or naver or local")
        String provider) {

    public Provider getProvider() {
        return Provider.from(provider);
    }
}
