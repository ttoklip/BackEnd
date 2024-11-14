package com.api.global.support.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessResponseStatus implements SuccessStatus {

    SUCCESS("200", "요청에 성공하였습니다.");

    private final String code;
    private final String message;
}
