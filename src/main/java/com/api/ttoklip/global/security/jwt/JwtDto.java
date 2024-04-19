package com.api.ttoklip.global.security.jwt;

public record JwtDto(String email, String provider) {

    public static JwtDto of(String email, String provider) {
        return new JwtDto(email, provider);
    }
}
