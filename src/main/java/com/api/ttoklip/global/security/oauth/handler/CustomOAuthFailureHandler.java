package com.api.ttoklip.global.security.oauth.handler;

import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomOAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
                                        final AuthenticationException authException) throws IOException {
        ApiException apiException = (ApiException) authException.getCause();
        ErrorType errorType = apiException.getErrorType();
        setResponse(response, errorType);
    }

    private void setResponse(HttpServletResponse response, ErrorType errorType) throws IOException {

        response.setContentType("application/json;charset=UTF-8");

        int status = Integer.parseInt(String.valueOf(errorType.getStatus()).substring(0,3));
        response.setStatus(status);

        response.getWriter().println(
                "{\"status\" : \"" +  status + "\"," +
                        "\"errorCode\" : \"" + errorType.getErrorCode() + "\"," +
                        " \"message\" : \"" + errorType.getMessage() +
                        "\"}");
    }
}