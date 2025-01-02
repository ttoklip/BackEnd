package com.api.global.security.handler;

import com.common.exception.ErrorType;

public record SecurityErrorResponse(int status, String errorCode, String message) {
    public static SecurityErrorResponse fromErrorType(final ErrorType errorType) {
        return new SecurityErrorResponse(
                errorType.getStatus(),
                errorType.getErrorCode(),
                errorType.getMessage()
        );
    }
}