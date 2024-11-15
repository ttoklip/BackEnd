package com.api.global.exception;


public record ApiExceptionResponse(
        int status,
        String errorCode,
        String message
) {}