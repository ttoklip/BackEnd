package com.api.auth.oauth2.presentation;

public record OAuthLoginResponse(String jwtToken, boolean ifFirstLogin) {

    public static OAuthLoginResponse of(
            String jwtToken,
            boolean ifFirstLogin
    ) {
        return new OAuthLoginResponse(
                jwtToken,
                ifFirstLogin
        );
    }
}
