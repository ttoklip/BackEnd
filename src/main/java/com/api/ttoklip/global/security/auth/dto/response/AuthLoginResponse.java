package com.api.ttoklip.global.security.auth.dto.response;

import lombok.Builder;

@Builder
public record AuthLoginResponse(String jwtToken, boolean ifFirstLogin) {
}
