package com.api.ttoklip.domain.manager.inquiry.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FaqCreateRequest {
    @Schema(description = "FaQ 제목", example = "FaQ 제목 예시")
    @NotEmpty
    @Size(max = 20)
    public String title;

    @Schema(description = "FaQ 내용", example = "FaQ 내용 예시")
    @NotEmpty
    @Size(max = 500)
    public String content;
}
