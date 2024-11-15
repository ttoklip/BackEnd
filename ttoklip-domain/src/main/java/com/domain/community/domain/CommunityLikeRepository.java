package com.domain.community.domain;

import java.util.Optional;

public interface CommunityLikeRepository {

    Optional<CommunityLike> findByCommunityIdAndMemberId(Long communityId, Long memberId);

    boolean existsByCommunityIdAndMemberId(Long postId, Long memberId);

    void deleteById(Long id);

    CommunityLike save(CommunityLike communityLike);

    Long countCommunityLikesByCommunityId(Long communityId);
}
