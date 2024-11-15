package com.domain.report.infrastructure;

import com.domain.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportJpaRepository extends JpaRepository<Report, Long> {
}
