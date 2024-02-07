package com.api.ttoklip.global.config.security.handler;

import com.api.ttoklip.domain.auth.dto.response.TokenMapping;
import com.api.ttoklip.domain.auth.service.CustomTokenProviderService;
import com.api.ttoklip.domain.user.domain.User;
import com.api.ttoklip.domain.user.domain.repository.UserRepository;
import com.api.ttoklip.global.DefaultAssert;
import com.api.ttoklip.global.config.security.OAuth2Config;
import com.api.ttoklip.global.config.security.token.UserPrincipal;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class CustomSimpleUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final CustomTokenProviderService customTokenProviderService;
    private final OAuth2Config oAuth2Config;
    private final UserRepository userRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultAssert.isAuthentication(!response.isCommitted());

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        TokenMapping token = customTokenProviderService.createToken(authentication);
        String userEmail = token.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        String providerId = userOptional.get().getProviderId();



        response.sendRedirect("http://localhost:3000/auth/oauth-response/" + token + "/3600" + "/providerId:" + providerId);

    }


    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
    }


}
