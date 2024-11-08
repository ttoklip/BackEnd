package com.api.global.success;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessResponseStatus implements SuccessStatus {

    SUCCESS("200", "요청에 성공하였습니다.");

    private final String code;
    private final String message;
}
