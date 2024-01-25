package com.api.ttoklip.domain.question.post.image.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class ImageResponse {
    @Schema(description = "포스트 이미지 인덱스", example = "1")
    private Long imageId;

    @Schema(description = "포스트 이미지 url")
    private String postImaUrl;
}
