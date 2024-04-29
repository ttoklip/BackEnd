package com.api.ttoklip.domain.member.repository;

import static com.api.ttoklip.domain.member.domain.QProfileLike.profileLike;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProfileLikeRepositoryImpl implements ProfileLikeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean isExists(final Long fromMemberId, final Long targetMemberId) {
        Long count = jpaQueryFactory
                .select(Wildcard.count)
                .from(profileLike)
                .where(
                        profileLike.fromMember.id.eq(fromMemberId),
                        profileLike.targetMember.id.eq(targetMemberId)
                )
                .fetchOne();

        return count > 0;
    }
}
