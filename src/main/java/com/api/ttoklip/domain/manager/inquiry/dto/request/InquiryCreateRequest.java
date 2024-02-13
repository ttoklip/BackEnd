package com.api.ttoklip.domain.manager.inquiry.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@AllArgsConstructor
@Builder
@Jacksonized
public class InquiryCreateRequest {
    @Schema(description = "문의사항 내용", example = "문의사항 내용 예시")
    @NotEmpty
    @Size(max = 300)
    public String content;
}
