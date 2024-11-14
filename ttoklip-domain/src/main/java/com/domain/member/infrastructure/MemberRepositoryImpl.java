package com.domain.member.infrastructure;

import com.domain.member.domain.Member;
import com.domain.member.domain.MemberRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository jpaRepository;
//    private final MemberQueryRepository queryDSLRepository;

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
//        return queryDSLRepository.getTargetMemberProfile(targetMemberId);
        return null;
    }

    @Override
    public Member findByIdWithProfile(final Long memberId) {
//        return queryDSLRepository.findByIdWithProfile(memberId);
        return null;
    }

    @Override
    public Member findByNickNameWithProfile(final String nickName) {
//        return queryDSLRepository.findByNickNameWithProfile(nickName);
        return null;
    }

    @Override
    public void save(final Member member) {
        jpaRepository.saveAndFlush(member);
    }

    @Override
    public List<Member> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void saveAll(final List<Member> members) {
        jpaRepository.saveAll(members);
    }
}
