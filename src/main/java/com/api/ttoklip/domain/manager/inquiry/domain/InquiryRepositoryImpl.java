package com.api.ttoklip.domain.manager.inquiry.domain;

import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.api.ttoklip.domain.manager.inquiry.domain.QInquiry.inquiry;

import java.util.Optional;
@RequiredArgsConstructor
public class InquiryRepositoryImpl implements InquiryRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public Inquiry findByIdActivated(final Long inquiryId) {
        Inquiry findInquiry = jpaQueryFactory
                .selectFrom(inquiry)
                .distinct()
                .where(
                        matchId(inquiryId), getInquiryActivate()
                )
                .fetchOne();
        return Optional.ofNullable(findInquiry)
                .orElseThrow(() -> new ApiException(ErrorType.INQUIRY_NOT_FOUND));
    }

    private BooleanExpression matchId(final Long inquiryId) {
        return inquiry.id.eq(inquiryId);
    }

    private BooleanExpression getInquiryActivate() {
        return inquiry.deleted.isFalse();
    }
}
