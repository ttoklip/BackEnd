package com.api.global.security;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class OAuthAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        if (authException.getCause() instanceof ApiException apiException) {
            ErrorType errorType = apiException.getErrorType();
            setResponse(response, errorType);
            return;
        }

        ApiException apiException = new ApiException(ErrorType._JWT_NOT_FOUND);
        ErrorType errorType = apiException.getErrorType();
        setResponse(response, errorType);
    }

    private void setResponse(HttpServletResponse response, ErrorType errorType) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        int status = errorType.getStatus();
        response.setStatus(status);

        response.getWriter().println(
                "{\"status\" : \"" + status + "\"," +
                        "\"errorCode\" : \"" + errorType.getErrorCode() + "\"," +
                        "\"message\" : \"" + errorType.getMessage() + "\"}");
    }
}
