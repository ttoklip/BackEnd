package com.api.ttoklip.global.exception;

import org.springframework.security.core.AuthenticationException;

public class DefaultAuthenticationException extends AuthenticationException {
    private ErrorType errorCode;

    public DefaultAuthenticationException(String msg, Throwable t) {
        super(msg, t);
        this.errorCode = ErrorType.INVALID_REPRESENTATION;
    }

    public DefaultAuthenticationException(String msg) {
        super(msg);
    }

    public DefaultAuthenticationException(ErrorType errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
