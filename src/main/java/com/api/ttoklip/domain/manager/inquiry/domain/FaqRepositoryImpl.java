package com.api.ttoklip.domain.manager.inquiry.domain;

import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.api.ttoklip.domain.manager.inquiry.domain.QFaq.faq;

@RequiredArgsConstructor
public class FaqRepositoryImpl implements FaqRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    public Faq findByIdActivated(final Long faqId) {
        Faq findFaq = jpaQueryFactory
                .selectFrom(faq)
                .distinct()
                .where(
                        matchId(faqId), getFaqActivate()
                )
                .fetchOne();
        return Optional.ofNullable(findFaq)
                .orElseThrow(() -> new ApiException(ErrorType.FAQ_NOT_FOUND));
    }

    private BooleanExpression matchId(final Long faqId) {
        return faq.id.eq(faqId);
    }

    private BooleanExpression getFaqActivate() {
        return faq.deleted.isFalse();
    }
}
