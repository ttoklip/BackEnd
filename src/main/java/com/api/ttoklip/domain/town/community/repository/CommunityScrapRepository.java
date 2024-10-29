package com.api.ttoklip.domain.town.community.repository;

import com.api.ttoklip.domain.town.community.domain.CommunityScrap;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityScrapRepository extends JpaRepository<CommunityScrap, Long>, CommunityScrapRepositoryCustom {
    Optional<CommunityScrap> findByCommunityIdAndMemberId(Long communityId, Long memberId);

    boolean existsByCommunityIdAndMemberId(Long postId, Long memberId);
}
