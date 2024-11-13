package com.api.community.presentation.dto.response;

import com.domain.community.domain.CommunityImage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommunityImageResponse {

    private Long communityImageId;
    private String communityImageUrl;

    public static CommunityImageResponse from(final CommunityImage image) {
        return CommunityImageResponse.builder()
                .communityImageId(image.getId())
                .communityImageUrl(image.getUrl())
                .build();
    }
}
