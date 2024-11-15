package com.api.auth.oauth2.presentation;

import lombok.Builder;

@Builder
public record OAuthLoginResponse(String jwtToken, boolean ifFirstLogin) {
}
