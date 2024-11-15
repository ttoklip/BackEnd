package com.domain.community.infrastructure;

import com.domain.community.domain.CommunityLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityLikeJpaRepository extends JpaRepository<CommunityLike, Long> {

    Optional<CommunityLike> findByCommunityIdAndMemberId(Long communityId, Long memberId);

    boolean existsByCommunityIdAndMemberId(Long postId, Long memberId);

    void deleteById(Long id);

    CommunityLike save(CommunityLike communityLike);

}
