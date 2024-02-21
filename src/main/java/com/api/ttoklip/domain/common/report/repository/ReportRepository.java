package com.api.ttoklip.domain.common.report.repository;

import com.api.ttoklip.domain.common.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
