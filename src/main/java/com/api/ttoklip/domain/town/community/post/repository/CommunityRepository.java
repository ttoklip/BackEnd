package com.api.ttoklip.domain.town.community.post.repository;

import com.api.ttoklip.domain.town.community.like.entity.CommunityLike;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Long>, CommunityRepositoryCustom {
//    Optional<CommunityLike> findByCommunityIdAndMemberId(Long communityId, Long memberId);

    boolean existsByCommunityIdAndMemberId(Long postId, Long memberId);
}
