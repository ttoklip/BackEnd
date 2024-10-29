package com.api.ttoklip.domain.town.community.repository;

import static com.api.ttoklip.domain.town.community.like.entity.QCommunityLike.communityLike;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommunityLikeRepositoryImpl implements CommunityLikeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long countCommunityLikesByCommunityId(final Long communityId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(communityLike)
                .where(communityLike.community.id.eq(communityId))
                .fetchOne();
    }
}
