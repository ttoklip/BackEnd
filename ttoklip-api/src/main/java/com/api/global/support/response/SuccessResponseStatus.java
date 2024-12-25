package com.api.global.support.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessResponseStatus implements SuccessStatus {

    SUCCESS(HttpStatus.OK, "요청에 성공하였습니다."),
    CREATED(HttpStatus.CREATED, "정상적으로 생성되었습니다."),
    ACCEPTED(HttpStatus.ACCEPTED, "요청이 접수되었으며, 백그라운드에서 처리가 진행 중입니다.");

    private final HttpStatus code;
    private final String message;
}
