package com.api.global.security.handler;

import com.common.exception.ErrorType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.http.MediaType;

public class SecurityErrorResponseWriter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private SecurityErrorResponseWriter() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void writeResponse(
            final HttpServletResponse response,
            final ErrorType errorType
    ) throws IOException {
        SecurityErrorResponse errorResponse = SecurityErrorResponse.fromErrorType(errorType);

//        response.setContentType("application/json;charset=UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(errorResponse.status());
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}