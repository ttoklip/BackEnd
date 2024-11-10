package com.domain.newsletter.application.response;


import com.domain.newsletter.domain.NewsletterUrl;

public record NewsletterUrlResponse(
        String url
) {
    public static NewsletterUrlResponse toDto(final NewsletterUrl newsletterUrl) {
        return new NewsletterUrlResponse(newsletterUrl.getUrl());
    }
}
