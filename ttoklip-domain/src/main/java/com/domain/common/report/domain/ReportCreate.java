package com.domain.common.report.domain;

public record ReportCreate(
        String content,

        ReportType reportType
) {
    public static ReportCreate of(String content, ReportType reportType) {
        return new ReportCreate(content, reportType);
    }
}