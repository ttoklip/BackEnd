package com.api.ttoklip.global.security.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {
    private String accessToken;
    private String provider;
}
