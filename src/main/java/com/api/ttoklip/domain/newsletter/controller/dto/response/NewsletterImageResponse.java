package com.api.ttoklip.domain.newsletter.controller.dto.response;

import com.api.ttoklip.domain.newsletter.domain.NewsletterImage;
import io.swagger.v3.oas.annotations.media.Schema;

public record NewsletterImageResponse(
        @Schema(description = "뉴스레터 이미지 url")
        String imageUrl
) {
    public static NewsletterImageResponse toDto(NewsletterImage newsletterImage) {
        return new NewsletterImageResponse(newsletterImage.getUrl());
    }
}
