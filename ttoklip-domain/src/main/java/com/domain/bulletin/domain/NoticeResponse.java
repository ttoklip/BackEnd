package com.domain.bulletin.domain;

import java.time.LocalDateTime;

public record NoticeResponse(
        Long noticeId,
        String title,
        String content,
        LocalDateTime createdAt
) {
    public static NoticeResponse from(final Notice notice) {
        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getCreatedDate()
        );
    }
}
