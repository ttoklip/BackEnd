package com.domain.bulletin.domain;

import java.util.List;

public record NoticeResponses(
        List<NoticeResponse> noticeResponses, boolean isLast
) {

    public static NoticeResponses from(List<Notice> notices, boolean isLast) {
        List<NoticeResponse> responses = notices.stream()
                .map(NoticeResponse::from)
                .toList();
        return new NoticeResponses(responses, isLast);
    }
}
