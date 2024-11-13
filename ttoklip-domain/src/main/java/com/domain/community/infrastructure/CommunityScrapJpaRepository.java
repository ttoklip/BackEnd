package com.domain.community.infrastructure;

import com.domain.community.domain.CommunityScrap;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityScrapJpaRepository extends JpaRepository<CommunityScrap, Long>{

    Optional<CommunityScrap> findByCommunityIdAndMemberId(Long communityId, Long memberId);

    boolean existsByCommunityIdAndMemberId(Long postId, Long memberId);

    CommunityScrap save(CommunityScrap communityScrap);

    void deleteById(Long id);
}
