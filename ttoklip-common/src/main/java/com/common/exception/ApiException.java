package com.common.exception;

public class ApiException extends RuntimeException {

    private final ErrorType errorType;

    public ApiException(final ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
