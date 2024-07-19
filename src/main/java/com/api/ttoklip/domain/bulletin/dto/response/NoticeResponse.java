package com.api.ttoklip.domain.bulletin.dto.response;

import com.api.ttoklip.domain.bulletin.domain.Notice;
import com.api.ttoklip.global.util.TimeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class NoticeResponse {
    @Schema(description = "공지사항 ID")
    private Long noticeId;

    @Schema(description = "공지사항 제목")
    private String title;

    @Schema(description = "공지사항 내용")
    private String content;

    @Schema(description = "공지사항 작성일자")
    private String createdAt;

    public static NoticeResponse of(final Notice notice) {

        String formattedCreatedDate = getFormattedCreatedDate(notice);

        return NoticeResponse.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdAt(formattedCreatedDate)
                .build();
    }

    private static String getFormattedCreatedDate(final Notice notice) {
        LocalDateTime createdDate = notice.getCreatedDate();
        return TimeUtil.formatCreatedDate(createdDate);
    }
}
