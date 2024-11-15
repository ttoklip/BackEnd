package com.common.exception;

import lombok.Getter;

@Getter
public class BadWordException extends RuntimeException {
    private final int status;
    private final String errorCode;
    private final String message;

    public BadWordException(final int status, final String errorCode, final String customMessage) {
        super(customMessage);
        this.status = status;
        this.errorCode = errorCode;
        this.message = customMessage;
    }
}
