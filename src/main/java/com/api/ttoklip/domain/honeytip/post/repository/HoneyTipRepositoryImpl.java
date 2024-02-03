package com.api.ttoklip.domain.honeytip.post.repository;


import static com.api.ttoklip.domain.honeytip.post.domain.QHoneyTip.*;

import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.post.domain.QHoneyTip;
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
                .selectFrom(honeyTip)
                .where(
                        matchId(honeyTipId), getHoneyTipActivate()
                )
                .fetchOne();
        return Optional.ofNullable(findHoneyTip)
                .orElseThrow(() -> new ApiException(ErrorType.HONEY_TIP_NOT_FOUND));
    }

    private BooleanExpression matchId(final Long honeyTipId) {
        return honeyTip.id.eq(honeyTipId);
    }

    private BooleanExpression getHoneyTipActivate() {
        return honeyTip.deleted.isFalse();
    }
}
