package com.api.ttoklip.global.security.jwt;

import com.api.ttoklip.global.exception.ApiException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    private final JwtProvider jwtProvider;
    private final AuthenticationFailureHandler failureHandler;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if (isPublicUri(requestURI)) {
            // Public uri 일 경우 검증 안함
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && isBearer(authorizationHeader)) {
            try {
                // "Bearer " 이후의 문자열을 추출
                String jwtToken = authorizationHeader.substring(7);

                // token 단순 유효성 검증
                jwtProvider.isValidToken(jwtToken);

                // token을 활용하여 유저 정보 검증
                jwtProvider.getAuthenticationFromToken(jwtToken);
            } catch (ApiException e) {
                failureHandler.onAuthenticationFailure(request, response,
                        new InsufficientAuthenticationException(e.getMessage(), e));
                return;
            }
        }

        filterChain.doFilter(request, response);

    }

    private boolean isBearer(final String authorizationHeader) {
        return authorizationHeader.startsWith("Bearer ");
    }

    private boolean isPublicUri(final String requestURI) {
        return
                requestURI.startsWith("/swagger-ui/**") ||
                        requestURI.startsWith("/health") ||
                        requestURI.startsWith("/v3/api-docs/**") ||
                        requestURI.startsWith("/favicon.ico") ||
                        requestURI.startsWith("/error") ||
                        requestURI.startsWith("/api/v1/auth") ||
                        requestURI.startsWith("/api/v1/join/**");
    }
}
