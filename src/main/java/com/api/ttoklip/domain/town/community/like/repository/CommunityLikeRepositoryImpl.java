package com.api.ttoklip.domain.town.community.like.repository;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.api.ttoklip.domain.town.community.like.entity.QCommunityLike.communityLike;

@RequiredArgsConstructor
public class CommunityLikeRepositoryImpl implements CommunityLikeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long countCommunityLikesByHoneyTipId(final Long communityId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(communityLike)
                .where(communityLike.community.id.eq(communityId))
                .fetchOne();
    }
}
