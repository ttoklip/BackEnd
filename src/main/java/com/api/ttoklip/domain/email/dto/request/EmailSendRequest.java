package com.api.ttoklip.domain.email.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class EmailSendRequest {

    @Schema(description = "이메일", example = "ttok123@naver.com")
    private String email;
}
