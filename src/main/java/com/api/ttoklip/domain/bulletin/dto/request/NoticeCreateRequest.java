package com.api.ttoklip.domain.bulletin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoticeCreateRequest {
    @Schema(description = "공지사항 제목", example = "공지사항 제목 예시")
    @NotEmpty
    @Size(max = 500)
    public String title;

    @Schema(description = "공지사항 내용", example = "공지사항 내용 예시")
    @NotEmpty
    @Size(max = 5000)
    public String content;
}
