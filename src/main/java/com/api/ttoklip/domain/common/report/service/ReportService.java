package com.api.ttoklip.domain.common.report.service;

import com.api.ttoklip.domain.common.report.domain.Report;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.repository.ReportRepository;
import com.api.ttoklip.domain.question.post.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public void reportQuestion(final ReportCreateRequest request, final Question question) {
        Report report = Report.questionOf(request, question);
        reportRepository.save(report);
    }
}
