package com.domain.interest.infrastructure;

import com.domain.interest.domain.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InterestJpaRepository extends JpaRepository<Interest, Long> {
    @Modifying
    @Query("DELETE FROM Interest i WHERE i.member.id = :memberId")
    void deleteAllByMemberId(@Param("memberId") Long memberId);
}
