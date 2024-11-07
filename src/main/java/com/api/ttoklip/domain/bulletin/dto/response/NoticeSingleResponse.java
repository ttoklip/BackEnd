package com.api.ttoklip.domain.bulletin.dto.response;

import com.api.ttoklip.domain.bulletin.domain.Notice;

public record NoticeSingleResponse(
        Long noticeId,
        String title,
        String content
) {
    public static NoticeSingleResponse noticeFrom(final Notice notice) {
        return new NoticeSingleResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent()
        );
    }
}
