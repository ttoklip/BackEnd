package com.api.ttoklip.domain.town.community.repository;

import com.api.ttoklip.domain.town.community.domain.CommunityLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityLikeRepository extends JpaRepository<CommunityLike, Long>, CommunityLikeRepositoryCustom {
    Optional<CommunityLike> findByCommunityIdAndMemberId(Long communityId, Long memberId);

    boolean existsByCommunityIdAndMemberId(Long postId, Long memberId);

}
