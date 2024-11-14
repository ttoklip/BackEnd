package com.domain.profile.infrastructure;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.profile.domain.ProfileLike;
import com.domain.profile.domain.QProfileLike;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProfileLikeQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QProfileLike profileLike = QProfileLike.profileLike;

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

    public Long countProfileLikesByMemberId(final Long targetMemberId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(profileLike)
                .where(profileLike.targetMember.id.eq(targetMemberId))
                .fetchOne();
    }

}
