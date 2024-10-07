package com.api.ttoklip.domain.honeytip.repository.like;

import static com.api.ttoklip.domain.honeytip.like.domain.QHoneyTipLike.honeyTipLike;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HoneyTipLikeRepositoryImpl implements HoneyTipLikeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long countHoneyTipLikesByHoneyTipId(final Long honeyTipId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(honeyTipLike)
                .where(honeyTipLike.honeyTip.id.eq(honeyTipId))
                .fetchOne();
    }

}
