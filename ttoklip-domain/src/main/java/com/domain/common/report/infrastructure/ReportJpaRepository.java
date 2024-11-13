package com.domain.common.report.infrastructure;

import com.domain.common.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportJpaRepository extends JpaRepository<Report, Long> {
}
