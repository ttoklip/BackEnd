package com.api.ttoklip.global.config.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class OAuth2Config {
    private final Auth auth = new Auth();

    @Data
    @NoArgsConstructor
    public static class Auth {
        private String tokenSecret;
        private long accessTokenExpirationMsec;
    }

    public Auth getAuth() {
        return auth;
    }
}
