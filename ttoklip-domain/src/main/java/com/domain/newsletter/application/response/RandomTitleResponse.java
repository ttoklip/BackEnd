package com.domain.newsletter.application.response;

import com.domain.newsletter.domain.TodayNewsletter;

public record RandomTitleResponse(
        Long newsletterId,
        String title,
        String mainImageUrl
) {
    public static RandomTitleResponse from(final TodayNewsletter todayNewsletter) {
        return new RandomTitleResponse(
                todayNewsletter.getNewsletter().getId(),
                todayNewsletter.getNewsletter().getTitle(),
                todayNewsletter.getNewsletter().getMainImageUrl()
        );
    }
}
