package com.api.ttoklip.domain.bulletin.dto.response;

import com.api.ttoklip.domain.bulletin.domain.Notice;
import com.api.ttoklip.global.util.TimeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record NoticeResponse(
        @Schema(description = "공지사항 ID") Long noticeId,
        @Schema(description = "공지사항 제목") String title,
        @Schema(description = "공지사항 내용") String content,
        @Schema(description = "공지사항 작성일자") String createdAt
) {
    public static NoticeResponse of(final Notice notice) {
        String formattedCreatedDate = getFormattedCreatedDate(notice);
        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                formattedCreatedDate
        );
    }

    private static String getFormattedCreatedDate(final Notice notice) {
        LocalDateTime createdDate = notice.getCreatedDate();
        return TimeUtil.formatCreatedDate(createdDate);
    }
}
