package com.api.ttoklip.domain.member.repository;

import static com.api.ttoklip.domain.member.domain.QProfileLike.profileLike;

import com.api.ttoklip.domain.member.domain.ProfileLike;
import com.api.ttoklip.domain.member.domain.QProfileLike;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
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

    @Override
    public ProfileLike findByFromMemberIdAndTargetMemberId(final Long fromMemberId, final Long targetMemberId) {
        ProfileLike profileLike = jpaQueryFactory
                .selectFrom(QProfileLike.profileLike)
                .where(
                        QProfileLike.profileLike.fromMember.id.eq(fromMemberId),
                        QProfileLike.profileLike.targetMember.id.eq(targetMemberId)
                )
                .fetchOne();

        return Optional.ofNullable(profileLike)
                .orElseThrow(() -> new ApiException(ErrorType.LIKE_NOT_FOUND));
    }

}
