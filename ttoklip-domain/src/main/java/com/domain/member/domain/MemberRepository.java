package com.domain.member.domain;

import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findByEmail(String email);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    Member getTargetMemberProfile(Long targetMemberId);

    Optional<Member> findById(Long memberId);

    Member findByIdWithProfile(Long memberId);

    Member findByNickNameWithProfile(String nickName);

    void save(Member member);
}
