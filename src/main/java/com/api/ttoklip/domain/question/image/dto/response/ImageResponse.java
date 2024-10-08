package com.api.ttoklip.domain.question.image.dto.response;

import com.api.ttoklip.domain.honeytip.domain.HoneyTipImage;
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

    @Schema(description = "포스트 이미지 id")
    private Long imageId;

    @Schema(description = "포스트 이미지 url")
    private String imageUrl;

    public static ImageResponse questionFrom(final QuestionImage image) {
        return ImageResponse.builder()
                .imageId(image.getId())
                .imageUrl(image.getUrl())
                .build();
    }

    public static ImageResponse honeyTipFrom(final HoneyTipImage image) {
        return ImageResponse.builder()
                .imageId(image.getId())
                .imageUrl(image.getUrl())
                .build();
    }

}
