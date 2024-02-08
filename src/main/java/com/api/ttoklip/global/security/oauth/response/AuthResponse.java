package com.api.ttoklip.global.security.oauth.response;

import lombok.Builder;

@Builder
public record AuthResponse(String token, Long memberId, String name, String profileImageUrl, boolean isFirstLogin) {
}
