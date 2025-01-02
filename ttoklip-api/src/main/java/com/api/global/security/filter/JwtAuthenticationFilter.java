package com.api.global.security.filter;

import com.api.global.security.SecurityConstants;
import com.api.global.security.handler.AuthEntryPointHandler;
import com.api.global.security.service.JwtAuthenticationService;
import com.common.exception.ApiException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationService jwtAuthenticationService;
    private final AuthEntryPointHandler authenticationEntryPoint;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final @NonNull HttpServletResponse response,
            final @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (isPublicUri(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
        if (authorizationHeader != null && isBearer(authorizationHeader)) {
            try {
                String jwtToken = authorizationHeader.substring(SecurityConstants.TOKEN_PREFIX_LENGTH);
                jwtAuthenticationService.authenticate(jwtToken);
            } catch (ApiException e) {
                authenticationEntryPoint.commence(
                        request, response, new AuthenticationException(e.getMessage(), e) {
                        });
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isBearer(final String authorizationHeader) {
        return authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX);
    }

    private boolean isPublicUri(final String requestURI) {
        return Arrays.stream(SecurityConstants.PUBLIC_ENDPOINTS)
                .anyMatch(requestURI::startsWith);
    }
}