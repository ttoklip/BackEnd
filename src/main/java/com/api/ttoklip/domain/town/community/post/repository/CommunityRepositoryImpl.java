package com.api.ttoklip.domain.town.community.post.repository;

import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Community findByIdFetchJoin(final Long communityPostId) {
        Community findCommunity = jpaQueryFactory
                .selectFrom(community)
                .leftJoin(community.communityImages, communityImage)
                .leftJoin(community.communityComments, communityComment)
                .fetchJoin()
                .where(community.id.eq(communityPostId))
                .orderBy(
                        communityComment.community.id.asc().nullsFirst(),
                        communityComment.createdDate.asc()
                )
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
