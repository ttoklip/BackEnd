package com.api.ttoklip.domain.SignUp.image.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class ImageResponse {
    private Long imageId;
    @Schema(description = "회원가입 이미지 url")
    private String postImaUrl;
}
