package com.api.ttoklip.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ApiExceptionResponse {

    private int status;

    private String errorCode;

    private String message;
}
