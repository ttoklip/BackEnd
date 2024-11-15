package com.common.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final ErrorType errorType;

    public ApiException(final ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

}
