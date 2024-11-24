package com.api.global.exception;

import com.common.config.event.Events;
import com.common.event.InternalServerErrorEvent;
import com.common.event.Modules;
import com.common.exception.ApiException;
import com.common.exception.BadWordException;
import com.common.exception.ErrorType;
import com.common.exception.HttpStatusCode;
import java.time.LocalDateTime;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ApiExceptionResponse> handleApiException(final ApiException e) {
        Modules modules = determineModuleFromException(e);
        if (e.getErrorType().getStatus() == HttpStatusCode.INTERNAL_SERVER_ERROR) {
            sendDiscord(e, modules);
        }

        ErrorType errorType = e.getErrorType();
        ApiExceptionResponse response = new ApiExceptionResponse(
                errorType.getStatus(),
                errorType.getErrorCode(),
                errorType.getMessage()
        );
        return ResponseEntity.status(errorType.getStatus()).body(response);
    }

    private void sendDiscord(final Throwable e, Modules module) {
        log.error("Unhandled exception occurred", e);
        Events.raise(new InternalServerErrorEvent(LocalDateTime.now(), e, module));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiExceptionResponse> handleInternalException(final Exception e) {
        Modules modules = determineModuleFromException(e);
        sendDiscord(e, modules);

        String INTERNAL_ERROR_CODE = "TTOKLIP_COMMON_INTERNAL_SERVER_ERROR";
        String INTERNAL_ERROR_MESSAGE = "알 수 없는 에러입니다. 관리자에게 문의하세요.";

        ApiExceptionResponse response = new ApiExceptionResponse(
                HttpStatusCode.INTERNAL_SERVER_ERROR,
                INTERNAL_ERROR_CODE,
                INTERNAL_ERROR_MESSAGE
        );
        return ResponseEntity.status(HttpStatusCode.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler({BadWordException.class})
    public ResponseEntity<ApiExceptionResponse> handleBadWordException(final BadWordException e) {
        ApiExceptionResponse response = new ApiExceptionResponse(
                e.getStatus(),
                e.getErrorCode(),
                e.getMessage()
        );
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    private Modules determineModuleFromException(Throwable e) {
        Throwable rootCause = getRootCause(e);

        return Arrays.stream(rootCause.getStackTrace())
                .map(StackTraceElement::getClassName)
                .map(this::getModuleFromClassName)
                .filter(module -> module != Modules.UNKNOWN)
                .findFirst()
                .orElse(Modules.UNKNOWN);
    }

    private Throwable getRootCause(Throwable e) {
        Throwable cause = e.getCause();
        if (cause != null && cause != e) {
            return getRootCause(cause);
        } else {
            return e;
        }
    }

    private Modules getModuleFromClassName(String className) {
        if (className.startsWith(Modules.API.getRoot())) {
            return Modules.API;
        } else if (className.startsWith(Modules.DOMAIN.getRoot())) {
            return Modules.DOMAIN;
        } else if (className.startsWith(Modules.INFRASTRUCTURE.getRoot())) {
            return Modules.INFRASTRUCTURE;
        } else if (className.startsWith(Modules.COMMON.getRoot())) {
            return Modules.COMMON;
        } else {
            return Modules.UNKNOWN;
        }
    }
}
