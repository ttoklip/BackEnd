package com.domain.community.infrastructure;


import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommunityScrapQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Long countCommunityScrapsByCommunityId(final Long communityId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(communityScrap)
                .where(communityScrap.community.id.eq(communityId))
                .fetchOne();
    }
}
