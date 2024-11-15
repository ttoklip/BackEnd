package com.domain.newsletter.application.response;

import com.domain.newsletter.domain.Newsletter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record NewsletterThumbnailResponse(
        Long newsletterId,
        String title,
        String mainImageUrl,
        String writtenTime
) {
    public static NewsletterThumbnailResponse from(final Newsletter newsletter) {
        String writtenTime = formatTime(newsletter.getCreatedDate());

        return new NewsletterThumbnailResponse(
                newsletter.getId(),
                newsletter.getTitle(),
                newsletter.getMainImageUrl(),
                writtenTime
        );
    }

    private static final String DATE_NONE = "날짜 없음";

    private static String formatTime(final LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return DATE_NONE;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return localDateTime.format(formatter);
    }
}
