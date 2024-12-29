package com.api.auth.oauth2.presentation;

public record OAuthLoginResponse(String jwtToken, boolean isFirstLogin) {

    public static OAuthLoginResponse of(
            String jwtToken,
            boolean isFirstLogin
    ) {
        return new OAuthLoginResponse(
                jwtToken,
                isFirstLogin
        );
    }
}
