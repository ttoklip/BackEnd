package com.api.ttoklip.domain.mypage.noti.post.dto.response;

import com.api.ttoklip.domain.mypage.noti.post.domain.Notice;
import com.api.ttoklip.domain.question.post.domain.Question;
import com.api.ttoklip.global.util.TimeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

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

    @Schema(description = "공지사항 작성일자")    private String createdAt;

    public static NoticeResponse of(final Notice notice){

        if (notice == null) {
            throw new NullPointerException("Notice 객체가 null입니다.");
        }

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
