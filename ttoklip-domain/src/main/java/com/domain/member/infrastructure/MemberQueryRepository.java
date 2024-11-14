package com.domain.member.infrastructure;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.interest.domain.QInterest;
import com.domain.member.domain.Member;
import com.domain.member.domain.QMember;
import com.domain.profile.domain.QProfile;
import com.domain.profile.domain.QProfileLike;
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
    private final QMember member = QMember.member;
    private final QInterest interest = QInterest.interest;

    public Member getTargetMemberProfile(final Long targetMemberId) {


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
