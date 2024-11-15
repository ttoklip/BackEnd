package com.domain.community.domain;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommunityScrapRepository {
    
    Optional<CommunityScrap> findByCommunityIdAndMemberId(Long communityId, Long memberId);

    boolean existsByCommunityIdAndMemberId(Long postId, Long memberId);

    CommunityScrap save(CommunityScrap communityScrap);

    void deleteById(Long id);

    Long countCommunityScrapsByCommunityId(Long communityId);

    Page<Community> getScrapPaging(Long memberId, Pageable pageable);
}
