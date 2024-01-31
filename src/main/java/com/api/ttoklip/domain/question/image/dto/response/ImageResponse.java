package com.api.ttoklip.domain.question.image.dto.response;

import com.api.ttoklip.domain.question.image.domain.QuestionImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageResponse {

    @Schema(description = "포스트 이미지 url")
    private String imageUrl;

    public static ImageResponse from(final QuestionImage image) {
        return ImageResponse.builder()
                .imageUrl(image.getUrl())
                .build();
    }
}
