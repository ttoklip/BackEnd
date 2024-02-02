package com.api.ttoklip.domain.town.community.image.dto.response;

import com.api.ttoklip.domain.town.community.image.entity.CommunityImage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageResponse {

    private String imageUrl;

    public static ImageResponse from(final CommunityImage image) {
        return ImageResponse.builder()
                .imageUrl(image.getUrl())
                .build();
    }
}
