package com.api.ttoklip.domain.member.repository;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.domain.QMember;
import com.api.ttoklip.domain.member.domain.QProfileLike;
import com.api.ttoklip.domain.privacy.domain.QProfile;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Member getTargetMemberProfile(final Long targetMemberId) {
        QMember member = QMember.member;
        QProfileLike profileLike = QProfileLike.profileLike;
        QProfile profile = QProfile.profile;

        Member findMember = jpaQueryFactory
                .select(member)
                .from(member)
                .leftJoin(member.profileLikesFrom, profileLike).fetchJoin()
                .leftJoin(member.profile, profile).fetchJoin()
                .where(member.id.eq(targetMemberId))
                .fetchOne();

        return Optional.ofNullable(findMember)
                .orElseThrow(() -> new ApiException(ErrorType._USER_NOT_FOUND_DB));
    }
}
