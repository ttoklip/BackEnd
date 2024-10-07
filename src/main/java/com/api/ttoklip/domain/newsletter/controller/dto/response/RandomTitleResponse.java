package com.api.ttoklip.domain.newsletter.controller.dto.response;

import com.api.ttoklip.domain.newsletter.domain.TodayNewsletter;

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
