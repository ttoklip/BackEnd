package com.api.ttoklip.domain.join.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(String jwtToken, boolean ifFirstLogin) {
}
