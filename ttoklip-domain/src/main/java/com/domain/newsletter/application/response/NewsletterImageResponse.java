package com.domain.newsletter.application.response;

import com.domain.newsletter.domain.NewsletterImage;

public record NewsletterImageResponse(
        String imageUrl
) {
    public static NewsletterImageResponse toDto(NewsletterImage newsletterImage) {
        return new NewsletterImageResponse(newsletterImage.getUrl());
    }
}
