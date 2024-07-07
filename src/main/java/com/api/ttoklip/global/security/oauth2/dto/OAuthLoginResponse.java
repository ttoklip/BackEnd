package com.api.ttoklip.global.security.oauth2.dto;

import lombok.Builder;

@Builder
public record OAuthLoginResponse(String jwtToken, boolean ifFirstLogin) {
}
