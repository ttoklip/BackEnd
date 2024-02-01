package com.api.ttoklip.domain.honeytip.post.post.repository;

import static com.api.ttoklip.domain.honeytip.post.post.domain.QHoneytip.honeytip;

import com.api.ttoklip.domain.honeytip.post.post.domain.HoneyTip;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HoneyTipRepositoryImpl implements HoneyTipRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public HoneyTip findByIdActivated(final Long honeyTipId) {
        HoneyTip findHoneyTip = jpaQueryFactory
                .selectFrom(honeytip)
                .where(
                        matchId(honeyTipId), getHoneyTipActivate()
                )
                .fetchOne();
        return Optional.ofNullable(findHoneyTip)
                .orElseThrow(() -> new ApiException(ErrorType.HONEY_TIP_NOT_FOUND));
    }

    private BooleanExpression matchId(final Long honeyTipId) {
        return honeytip.id.eq(honeyTipId);
    }

    private BooleanExpression getHoneyTipActivate() {
        return honeytip.deleted.isFalse();
    }
}
