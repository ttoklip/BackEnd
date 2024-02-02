package com.api.ttoklip.domain.town.community.post.repository;

import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.global.exception.ApiException;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Community findByIdUndeleted(final Long communityId) {
        Community findCommunity = jpaQueryFactory
                .selectFrom(community)
                .where(findUnDeleted(), matchId(communityId))
                .fetchOne();

        return Optional.ofNullable(findCommunity)
                .orElseThrow(() -> new ApiException(ErrorType.COMMUNITY_NOT_FOUNT));
    }

    private BooleanExpression findUnDeleted() {
        return community.deleted.isFalse();
    }

    private BooleanExpression matchId(final Long questionId) {
        return community.id.eq(questionId);
    }
}
