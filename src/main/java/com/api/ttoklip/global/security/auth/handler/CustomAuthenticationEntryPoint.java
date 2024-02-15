package com.api.ttoklip.global.security.auth.handler;

import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        if (authException.getCause() instanceof ApiException) {
            ApiException apiException = (ApiException) authException.getCause();
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

        int status = errorType.getStatus().value(); // Spring 5 이상에서는 HttpStatus enum을 사용하여 상태 코드를 가져옵니다.
        response.setStatus(status);

        response.getWriter().println(
                "{\"status\" : \"" + status + "\"," +
                        "\"errorCode\" : \"" + errorType.getErrorCode() + "\"," +
                        "\"message\" : \"" + errorType.getMessage() + "\"}");
    }
}
