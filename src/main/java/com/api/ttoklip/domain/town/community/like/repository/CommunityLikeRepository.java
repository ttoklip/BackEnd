package com.api.ttoklip.domain.town.community.like.repository;

import com.api.ttoklip.domain.town.community.like.entity.CommunityLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityLikeRepository extends JpaRepository<CommunityLike, Long>, CommunityLikeRepositoryCustom {
    Optional<CommunityLike> findByCommunityIdAndMemberId(Long communityId, Long memberId);

    boolean existsByCommunityIdAndMemberId(Long postId, Long memberId);

}
