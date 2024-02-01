package com.api.ttoklip.domain.newsletter.image.dto.response;

import com.api.ttoklip.domain.newsletter.post.domain.NewsletterImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageRes {

    @Schema(description = "뉴스레터 이미지 url")
    private String imageUrl;

    public static ImageRes toDto(final NewsletterImage newsletterImage) {
        return ImageRes.builder()
                .imageUrl(newsletterImage.getUrl())
                .build();
    }
}
