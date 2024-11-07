package com.api.ttoklip.domain.term.repository;

import com.api.ttoklip.domain.term.domain.TermAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermAgreementRepository extends JpaRepository<TermAgreement, Long> {
//    boolean existsByMemberIdAndTermId(Long memberId, Long termId);
}
