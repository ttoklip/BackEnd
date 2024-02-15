package com.api.ttoklip.domain.town.community.scrap.repository;

import com.api.ttoklip.domain.town.community.like.repository.CommunityLikeRepositoryCustom;
import com.api.ttoklip.domain.town.community.scrap.entity.CommunityScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityScrapRepository extends JpaRepository<CommunityScrap, Long>, CommunityScrapRepositoryCustom {
    Optional<CommunityScrap> findByCommunityIdAndMemberId(Long communityId, Long memberId);

    boolean existsByCommunityIdAndMemberId(Long postId, Long memberId);
}
