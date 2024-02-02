package com.api.ttoklip.domain.mypage.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class NoticeResponse {
    @Schema(description = "공지사항 ID")
    private Long id;

    @Schema(description = "공지사항 제목")
    private String title;

    @Schema(description = "공지사항 내용")
    private String content;

    @Schema(description = "공지사항 작성일자")
    private String createdAt;
}
