package com.api.ttoklip.domain.honeytip.post.post.domain.repository;

import static com.api.ttoklip.domain.honeytip.post.post.domain.QHoneytip.honeytip;

import com.api.ttoklip.domain.honeytip.post.post.domain.Honeytip;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HoneytipRepositoryImpl implements HoneytipRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Honeytip findByIdActivated(final Long honneytipId) {
        Honeytip findHoneytip = jpaQueryFactory
                .selectFrom(honeytip)
                .where(
                        matchId(honneytipId), getHoneytipActivate()
                )
                .fetchOne();
        return Optional.ofNullable(findHoneytip)
                .orElseThrow(() -> new ApiException(ErrorType.HONEY_TIP_NOT_FOUND));
    }

    private BooleanExpression matchId(final Long honneytipId) {
        return honeytip.id.eq(honneytipId);
    }

    private BooleanExpression getHoneytipActivate() {
        return honeytip.deleted.isFalse();
    }
}
