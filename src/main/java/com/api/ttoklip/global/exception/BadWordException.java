package com.api.ttoklip.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadWordException extends RuntimeException {
    private final HttpStatus status;
    private final String errorCode;
    private final String message;

    public BadWordException(final HttpStatus status, final String errorCode, final String customMessage) {
        super(customMessage);  // 여기서 customMessage를 설정
        this.status = status;
        this.errorCode = errorCode;
        this.message = customMessage;
    }
}
