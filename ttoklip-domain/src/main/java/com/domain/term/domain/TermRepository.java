package com.domain.term.domain;

import java.util.List;
import java.util.Optional;

public interface TermRepository {

    Term getAgreeTermsOfService();

    Term getAgreePrivacyPolicy();

    Term getAgreeLocationService();

    Optional<Term> findById(Long id);

    void saveAndFlush(Term term);

    List<Term> getAllTerms();
}
