package com.api.ttoklip.domain.town.community.repository.like;

import com.api.ttoklip.domain.town.community.domain.CommunityLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityLikeJpaRepository extends JpaRepository<CommunityLike, Long> {

    Optional<CommunityLike> findByCommunityIdAndMemberId(Long communityId, Long memberId);

    boolean existsByCommunityIdAndMemberId(Long postId, Long memberId);

    void deleteById(Long id);

    CommunityLike save(CommunityLike communityLike);

}
