package com.api.global.security.handler;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException authException
    ) throws IOException {
        // ApiException일 경우
        if (authException.getCause() instanceof ApiException apiException) {
            handleApiException(response, apiException);
            return;
        }

        // JWT 토큰이 없는 경우
        handleDefaultException(response);
    }

    private void handleApiException(
            final HttpServletResponse response,
            final ApiException apiException
    ) throws IOException {
        SecurityErrorResponseWriter.writeResponse(response, apiException.getErrorType());
    }

    private void handleDefaultException(
            final HttpServletResponse response
    ) throws IOException {
        SecurityErrorResponseWriter.writeResponse(response, ErrorType._JWT_NOT_FOUND);
    }
}
