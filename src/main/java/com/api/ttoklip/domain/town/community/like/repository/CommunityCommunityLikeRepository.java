package com.api.ttoklip.domain.town.community.like.repository;

import com.api.ttoklip.domain.town.community.like.entity.CommunityLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityCommunityLikeRepository extends JpaRepository<CommunityLike, Long>, CommunityLikeRepositoryCustom {
    Optional<CommunityLike> findByCommunityIdAndMemberId(Long communityId, Long memberId);

    boolean existsByCommunityIdAndMemberId(Long postId, Long memberId);

}
