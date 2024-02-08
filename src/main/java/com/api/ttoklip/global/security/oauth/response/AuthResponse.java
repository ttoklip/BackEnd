package com.api.ttoklip.global.security.oauth.response;

import lombok.Builder;

@Builder
public record AuthResponse(Long memberId, String memberEmail, String nickname, String profileImageUrl) {
}
