package com.api.ttoklip.domain.member.repository;

import com.api.ttoklip.domain.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
//    Optional<Member> findByNaverEmail(String email);
//    Optional<Member> findByKakaoId(Long kakaoId);

    Optional<Member> findByEmail(String email);

    boolean existsByNickname(String nickname);

}
