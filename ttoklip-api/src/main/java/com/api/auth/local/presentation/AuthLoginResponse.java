package com.api.auth.local.presentation;

import lombok.Builder;

@Builder
public record AuthLoginResponse(String jwtToken, boolean ifFirstLogin) {
}
