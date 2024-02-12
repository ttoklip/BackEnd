package com.api.ttoklip.global.security.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponse {
    private String jwtToken;
    private boolean ifFirstLogin;
}
