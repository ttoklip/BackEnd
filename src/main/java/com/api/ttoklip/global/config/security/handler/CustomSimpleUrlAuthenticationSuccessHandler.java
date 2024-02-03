package com.api.ttoklip.global.config.security.handler;

import com.api.ttoklip.domain.auth.dto.response.TokenMapping;
import com.api.ttoklip.domain.auth.service.CustomTokenProviderService;
import com.api.ttoklip.global.config.security.OAuth2Config;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomSimpleUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final CustomTokenProviderService customTokenProviderService;
    private final OAuth2Config oAuth2Config;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        TokenMapping token = customTokenProviderService.createToken(authentication);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

    }
}
