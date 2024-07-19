package com.api.ttoklip.domain.term.repository;

import com.api.ttoklip.domain.term.domain.Term;
public interface TermRepositoryCustom {
    Term getAgreeTermsOfService();

    Term getAgreePrivacyPolicy();

    Term getAgreeLocationService();
}
