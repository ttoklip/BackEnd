package com.api.ttoklip.domain.town.community.image;

import com.api.ttoklip.domain.town.community.image.entity.Image;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageResponse {

    @Schema(description = "포스트 이미지 인덱스", example = "1")
    private Long id;

    @Schema(description = "포스트 이미지 url")
    private String filepath;

    public ImageResponse(Image image) {
        this.id = image.getId();
        this.filepath = image.getFilePath();
    }
}
