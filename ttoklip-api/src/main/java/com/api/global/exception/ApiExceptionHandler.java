package com.api.global.exception;

import com.api.presentation.SlackService;
import com.common.config.event.Events;
import com.common.event.InternalServerExceptionEvent;
import com.common.event.Modules;
import com.common.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private final SlackService slackService;

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ApiExceptionResponse> handleApiException(final ApiException e) {
        return buildAndSendSlackAlert(e.getErrorType().getStatus(), e.getErrorType().getErrorCode(), e.getErrorType().getMessage(), e);
    }

    @ExceptionHandler({BadWordException.class})
    public ResponseEntity<ApiExceptionResponse> handleBadWordException(final BadWordException e) {
        return buildAndSendSlackAlert(e.getStatus(), e.getErrorCode(), e.getMessage(), e);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiExceptionResponse> handleInternalException(final Exception e) {
        String INTERNAL_ERROR_CODE = "TTOKLIP_COMMON_INTERNAL_SERVER_ERROR";
        String INTERNAL_ERROR_MESSAGE = "알 수 없는 에러입니다. 관리자에게 문의하세요.";
        return buildAndSendSlackAlert(HttpStatusCode.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_CODE, INTERNAL_ERROR_MESSAGE, e);
    }

    private ResponseEntity<ApiExceptionResponse> buildAndSendSlackAlert(int statusCode, String errorCode, String errorMessage, Throwable e) {
        Modules modules = ErrorRootFinder.determineModuleFromException(e);

        // Slack 알림 전송
        slackService.sendErrorMessage("API Exception Occurred", e, modules, statusCode);

        // 로그 기록
        log.error("Exception occurred: {}", e.getMessage(), e);

        // 에러 응답 생성
        ApiExceptionResponse response = new ApiExceptionResponse(
                statusCode,
                errorCode,
                errorMessage
        );

        // 이벤트 발생 (InternalServerExceptionEvent는 상태 코드와 무관하게 사용 가능)
        Events.raise(new InternalServerExceptionEvent(LocalDateTime.now(), e, modules));

        return ResponseEntity.status(statusCode).body(response);
    }
}
