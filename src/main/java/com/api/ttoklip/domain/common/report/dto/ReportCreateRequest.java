package com.api.ttoklip.domain.common.report.dto;

import com.api.ttoklip.domain.common.report.domain.ReportType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportCreateRequest {

    private String content;

    private ReportType reportType;
}
