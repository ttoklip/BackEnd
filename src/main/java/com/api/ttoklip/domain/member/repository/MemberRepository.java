package com.api.ttoklip.domain.member.repository;

import com.api.ttoklip.domain.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByNaverEmail(String email);
    Optional<Member> findByKakaoId(Long kakaoId);
    boolean existsByNickname(String nickname);

}
