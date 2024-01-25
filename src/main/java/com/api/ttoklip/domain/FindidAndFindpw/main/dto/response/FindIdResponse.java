package com.api.ttoklip.domain.FindidAndFindpw.main.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FindIdResponse {
    @Schema(description = "유저의 아이디 입니다")
    private String userAuth;
}
