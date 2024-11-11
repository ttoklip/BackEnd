package com.domain.term.domain;

import java.util.List;

public interface TermAgreementRepository {
    void saveAll(List<TermAgreement> termAgreements);
}
