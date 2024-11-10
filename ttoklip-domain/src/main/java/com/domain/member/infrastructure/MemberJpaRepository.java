package com.domain.member.infrastructure;

import com.domain.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

}
