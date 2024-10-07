package com.api.ttoklip.domain.newsletter.controller.dto.response;

import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import com.api.ttoklip.global.util.TimeUtil;

public record NewsletterThumbnailResponse(
        Long newsletterId,
        String title,
        String mainImageUrl,
        String writtenTime
) {
    public static NewsletterThumbnailResponse from(final Newsletter newsletter) {
        String writtenTime = TimeUtil.newsletterTimeFormat(newsletter.getCreatedDate());

        return new NewsletterThumbnailResponse(
                newsletter.getId(),
                newsletter.getTitle(),
                newsletter.getMainImageUrl(),
                writtenTime
        );
    }
}
