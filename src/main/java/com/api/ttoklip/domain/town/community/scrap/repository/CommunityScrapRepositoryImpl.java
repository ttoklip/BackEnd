package com.api.ttoklip.domain.town.community.scrap.repository;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.api.ttoklip.domain.town.community.scrap.entity.QCommunityScrap.communityScrap;

@RequiredArgsConstructor
public class CommunityScrapRepositoryImpl implements CommunityScrapRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long countCommunityScrapsByCommunityId(final Long communityId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(communityScrap)
                .where(communityScrap.community.id.eq(communityId))
                .fetchOne();
    }
}
