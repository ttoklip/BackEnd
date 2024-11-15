package com.api.global.jwt;

import com.common.exception.ApiException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationService jwtAuthenticationService;
    private final AuthenticationFailureHandler failureHandler;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (isPublicUri(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authorizationHeader != null && isBearer(authorizationHeader)) {
            try {
                String jwtToken = authorizationHeader.substring(7);
                jwtAuthenticationService.authenticate(jwtToken);
            } catch (ApiException e) {
                failureHandler.onAuthenticationFailure(request, response,
                        new InsufficientAuthenticationException(e.getMessage(), e));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isBearer(String authorizationHeader) {
        return authorizationHeader.startsWith(TOKEN_PREFIX);
    }

    private boolean isPublicUri(String requestURI) {
        return requestURI.startsWith("/swagger-ui/**") ||
                requestURI.startsWith("/health") ||
                requestURI.startsWith("/v3/api-docs/**") ||
                requestURI.startsWith("/favicon.ico") ||
                requestURI.startsWith("/error") ||
                requestURI.startsWith("/actuator/prometheus") ||
                requestURI.startsWith("/actuator") ||
                requestURI.startsWith("/api/v1/auth");
    }
}