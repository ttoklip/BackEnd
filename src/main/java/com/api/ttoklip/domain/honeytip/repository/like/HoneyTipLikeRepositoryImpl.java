package com.api.ttoklip.domain.honeytip.repository.like;

import com.api.ttoklip.domain.honeytip.domain.QHoneyTipLike;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HoneyTipLikeRepositoryImpl implements HoneyTipLikeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private final QHoneyTipLike honeyTipLike = QHoneyTipLike.honeyTipLike;

    @Override
    public Long countHoneyTipLikesByHoneyTipId(final Long postId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(honeyTipLike)
                .where(honeyTipLike.honeyTip.id.eq(postId))
                .fetchOne();
    }

}
