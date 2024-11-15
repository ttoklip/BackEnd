package com.domain.community.infrastructure;


import com.domain.community.domain.QCommunityLike;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommunityLikeQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QCommunityLike communityLike = QCommunityLike.communityLike;

    public Long countCommunityLikesByCommunityId(final Long communityId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(communityLike)
                .where(communityLike.community.id.eq(communityId))
                .fetchOne();
    }
}
