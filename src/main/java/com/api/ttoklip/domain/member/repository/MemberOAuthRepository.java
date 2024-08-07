package com.api.ttoklip.domain.member.repository;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.domain.QMember;
import com.api.ttoklip.domain.privacy.domain.QProfile;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberOAuthRepository {
    private final JPAQueryFactory jpaQueryFactory;

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
