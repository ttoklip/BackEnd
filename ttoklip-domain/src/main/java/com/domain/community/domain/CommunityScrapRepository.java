package com.domain.community.domain;

import java.util.Optional;

public interface CommunityScrapRepository {
    
    Optional<CommunityScrap> findByCommunityIdAndMemberId(Long communityId, Long memberId);

    boolean existsByCommunityIdAndMemberId(Long postId, Long memberId);

    CommunityScrap save(CommunityScrap communityScrap);

    void deleteById(Long id);

    Long countCommunityScrapsByCommunityId(Long communityId);
}
