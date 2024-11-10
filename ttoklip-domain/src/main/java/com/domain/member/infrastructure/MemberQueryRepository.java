package com.domain.member.infrastructure;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.member.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QProfile profile = QProfile.profile;
    private final QProfileLike profileLike = QProfileLike.profileLike;

    public Member getTargetMemberProfile(final Long targetMemberId) {
        QMember member = QMember.member;
        QProfileLike profileLike = QProfileLike.profileLike;
        QProfile profile = QProfile.profile;
        QInterest interest = QInterest.interest;

        Member findMember = jpaQueryFactory
                .select(member)
                .from(member)
                .leftJoin(member.profileLikesFrom, profileLike).fetchJoin()
                .leftJoin(member.profile, profile).fetchJoin()
                .leftJoin(member.interests, interest)
                .where(member.id.eq(targetMemberId))
                .fetchOne();

        return Optional.ofNullable(findMember)
                .orElseThrow(() -> new ApiException(ErrorType._USER_NOT_FOUND_DB));
    }

    public Member findByIdWithProfile(final Long inputId) {

        QMember member = QMember.member;
        QProfile profile = QProfile.profile;

        Member findMember = jpaQueryFactory
                .selectFrom(member)
                .distinct()
                .leftJoin(member.profile, profile).fetchJoin()
                .where(member.id.eq(inputId))
                .fetchOne();

        return Optional.ofNullable(findMember)
                .orElseThrow(() -> new ApiException(ErrorType._USER_NOT_FOUND_DB));
    }

    public Member findByNickNameWithProfile(final String nickName) {//02.17
        QMember member = QMember.member;
        QProfile profile = QProfile.profile;

        Member findMember = jpaQueryFactory
                .selectFrom(member)
                .distinct()
                .leftJoin(member.profile, profile).fetchJoin()
                .where(member.nickname.eq(nickName))
                .fetchOne();

        return Optional.ofNullable(findMember)
                .orElseThrow(() -> new ApiException(ErrorType._USER_NOT_FOUND_DB));

    }
}