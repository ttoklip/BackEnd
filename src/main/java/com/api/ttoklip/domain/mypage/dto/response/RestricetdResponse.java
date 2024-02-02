package com.api.ttoklip.domain.mypage.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RestricetdResponse {
    @Schema(description = "제ㅔ제기간")
    private String time;
    private String reason;
}
