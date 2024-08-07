package com.api.ttoklip.domain.bulletin.dto.response;

import com.api.ttoklip.domain.bulletin.domain.Notice;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NoticeSingleResponse {
    private Long noticeId;
    private String title;
    private String content;

    public static NoticeSingleResponse noticeFrom(final Notice notice) {
        return NoticeSingleResponse.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .build();
    }
}
