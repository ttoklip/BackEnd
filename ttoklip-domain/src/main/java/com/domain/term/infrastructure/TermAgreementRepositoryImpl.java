package com.domain.term.infrastructure;

import com.domain.term.domain.TermAgreement;
import com.domain.term.domain.TermAgreementRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TermAgreementRepositoryImpl implements TermAgreementRepository {

    private final TermAgreementJpaRepository jpaRepository;

    @Override
    public void saveAll(final List<TermAgreement> termAgreements) {
        jpaRepository.saveAll(termAgreements);
    }
}
