package com.api.ttoklip.domain.member.repository;

import com.api.ttoklip.domain.member.domain.Member;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository{

    private final MemberJpaRepository jpaRepository;
    private final MemberQueryRepository queryDSLRepository;

    @Override
    public Optional<Member> findById(final Long memberId) {
        return jpaRepository.findById(memberId);
    }

    @Override
    public Optional<Member> findByEmail(final String email) {
        return jpaRepository.findByEmail(email);
    }

    @Override
    public boolean existsByNickname(final String nickname) {
        return jpaRepository.existsByNickname(nickname);
    }

    @Override
    public boolean existsByEmail(final String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public Member getTargetMemberProfile(final Long targetMemberId) {
        return queryDSLRepository.getTargetMemberProfile(targetMemberId);
    }

    @Override
    public Member findByIdWithProfile(final Long memberId) {
        return queryDSLRepository.findByIdWithProfile(memberId);
    }

    @Override
    public Member findByNickNameWithProfile(final String nickName) {
        return queryDSLRepository.findByNickNameWithProfile(nickName);
    }

    @Override
    public void save(final Member member) {
        jpaRepository.save(member);
    }
}
