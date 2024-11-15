package com.domain.term.infrastructure;

import com.domain.term.domain.TermAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermAgreementJpaRepository extends JpaRepository<TermAgreement, Long> {
}
