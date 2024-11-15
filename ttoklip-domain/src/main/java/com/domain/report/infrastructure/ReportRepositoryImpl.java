package com.domain.report.infrastructure;

import com.domain.report.domain.Report;
import com.domain.report.domain.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepository {

    private final ReportJpaRepository jpaRepository;

    @Override
    public void save(final Report report) {
        jpaRepository.save(report);
    }
}
