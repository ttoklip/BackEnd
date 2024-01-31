package com.api.ttoklip.domain.common.report.dto;

import com.api.ttoklip.domain.common.report.domain.ReportType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportCreateRequest {

    @Schema(description = "신고할 내용", example = "이 게시글은 부적절한 내용을 포함하고 있습니다.")
    private String content;

    @Schema(description = "신고 타입", example = "INAPPROPRIATE_CONTENT",
            allowableValues = {
                    "FISHING_DUPLICATE_SPAM", "COMMERCIAL_ADVERTISING",
                    "INAPPROPRIATE_CONTENT", "ABUSE", "RELIGIOUS_PROSELYTIZING",
                    "INAPPROPRIATE_FOR_FORUM", "LEAK_IMPERSONATION_FRAUD"
            })
    private String reportType;

    public ReportType getReportType() {
        return ReportType.findReportTypeByValue(reportType);
    }
}
