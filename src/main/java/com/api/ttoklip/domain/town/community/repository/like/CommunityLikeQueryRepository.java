package com.api.ttoklip.domain.town.community.repository.like;


import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.api.ttoklip.domain.town.community.domain.QCommunityLike.communityLike;

@Repository
@RequiredArgsConstructor
public class CommunityLikeQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Long countCommunityLikesByCommunityId(final Long communityId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(communityLike)
                .where(communityLike.community.id.eq(communityId))
                .fetchOne();
    }
}
