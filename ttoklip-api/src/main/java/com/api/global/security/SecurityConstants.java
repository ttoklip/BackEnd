package com.api.global.security;

public class SecurityConstants {

    public static final String[] PUBLIC_ENDPOINTS = {
            "/favicon.ico",
            "/health",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api/v1/auth/**",
            "/api/v1/oauth",
            "/api/v1/email/**",
            "/actuator/**",
            "/api/v1/privacy/local/check-nickname"
    };

    public static final String[] ADMIN_ENDPOINTS = {
            "/api/v1/admin/**",
            "/error/**",
            "/api/v1/member/ban"
    };

    public static final String[] MANAGER_POST_ENDPOINTS = {
            "/api/v1/newsletter/posts"
    };

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final int TOKEN_PREFIX_LENGTH = TOKEN_PREFIX.length();
    public static final String ROLE_PREFIX = "ROLE_";
}
