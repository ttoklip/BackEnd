package com.domain.term.application;

import com.domain.term.domain.TermAgreement;
import com.domain.term.domain.TermAgreementRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermAgreementService {

    private final TermAgreementRepository termAgreementRepository;

    @Transactional
    public void regsiterAgreements(final List<TermAgreement> termAgreements) {
        termAgreementRepository.saveAll(termAgreements);
    }
}
