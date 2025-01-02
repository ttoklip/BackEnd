package com.api.global.security.handler;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AccessDeniedException accessDeniedException
    ) throws IOException {
        ApiException apiException = new ApiException(ErrorType._NOT_ADMIN_USER);
        ErrorType errorType = apiException.getErrorType();

        SecurityErrorResponseWriter.writeResponse(response, errorType);
    }
}
