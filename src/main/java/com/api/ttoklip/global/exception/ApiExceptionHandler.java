package com.api.ttoklip.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ApiExceptionResponse> handleApiException(ApiException e) {
        ErrorType errorType = e.getErrorType();
        ApiExceptionResponse response = new ApiExceptionResponse(
                errorType.getStatus().value(),
                errorType.getErrorCode(),
                errorType.getMessage()
        );
        return ResponseEntity.status(errorType.getStatus()).body(response);
    }
}
