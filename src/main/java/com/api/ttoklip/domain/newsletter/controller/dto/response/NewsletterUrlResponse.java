package com.api.ttoklip.domain.newsletter.controller.dto.response;

import com.api.ttoklip.domain.newsletter.domain.NewsletterUrl;
import io.swagger.v3.oas.annotations.media.Schema;

public record NewsletterUrlResponse(
        @Schema(description = "Url 링크")
        String url
) {
    public static NewsletterUrlResponse toDto(final NewsletterUrl newsletterUrl) {
        return new NewsletterUrlResponse(newsletterUrl.getUrl());
    }
}
