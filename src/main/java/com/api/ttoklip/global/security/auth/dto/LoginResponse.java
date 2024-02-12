package com.api.ttoklip.global.security.auth.dto;

import lombok.Builder;

@Builder
public record LoginResponse(String jwtToken, boolean ifFirstLogin) {
}
