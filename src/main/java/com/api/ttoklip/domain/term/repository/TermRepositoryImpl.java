package com.api.ttoklip.domain.term.repository;

import com.api.ttoklip.domain.term.domain.QTerm;
import com.api.ttoklip.domain.term.domain.Term;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TermRepositoryImpl implements TermRepositoryCustom {

    private static final String agreeTermsOfService = "서비스 이용약관";
    private static final String agreePrivacyPolicy = "개인정보 처리방침";
    private static final String agreeLocationService = "위치서비스 이용약관";


    private final JPAQueryFactory jpaQueryFactory;
    private final QTerm term = QTerm.term;

    @Override
    public Term getAgreeTermsOfService() {
        Term findterm = jpaQueryFactory
                .selectFrom(term)
                .where(term.title.like(agreeTermsOfService))
                .fetchOne();

        return Optional.ofNullable(findterm)
                .orElseThrow(() -> new ApiException(ErrorType.TERM_SERVICE_NOT_FOUND));
    }

    @Override
    public Term getAgreePrivacyPolicy() {
        Term findterm = jpaQueryFactory
                .selectFrom(term)
                .where(term.title.like(agreePrivacyPolicy))
                .fetchOne();

        return Optional.ofNullable(findterm)
                .orElseThrow(() -> new ApiException(ErrorType.TERM_PRIVACY_POLICY_NOT_FOUND));
    }

    @Override
    public Term getAgreeLocationService() {
        Term findterm = jpaQueryFactory
                .selectFrom(term)
                .where(term.title.like(agreeLocationService))
                .fetchOne();

        return Optional.ofNullable(findterm)
                .orElseThrow(() -> new ApiException(ErrorType.TERM_LOCATIONS_SERVICE_NOT_FOUND));
    }
}
