package com.api.common;

import com.domain.report.domain.ReportType;
import io.swagger.v3.oas.annotations.media.Schema;

public record ReportWebCreate(
        @Schema(description = "신고할 내용", example = "이 게시글은 부적절한 내용을 포함하고 있습니다.")
        String content,

        @Schema(description = "신고 타입", example = "INAPPROPRIATE_CONTENT",
                allowableValues = {
                        "FISHING_DUPLICATE_SPAM", "COMMERCIAL_ADVERTISING",
                        "INAPPROPRIATE_CONTENT", "ABUSE", "RELIGIOUS_PROSELYTIZING",
                        "INAPPROPRIATE_FOR_FORUM", "LEAK_IMPERSONATION_FRAUD"
                })
        String reportType
) {
    public ReportType getReportType() {
        return ReportType.findReportTypeByValue(reportType);
    }
}