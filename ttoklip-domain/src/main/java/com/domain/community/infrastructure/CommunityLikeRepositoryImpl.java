package com.domain.community.infrastructure;

import com.domain.community.domain.CommunityLike;
import com.domain.community.domain.CommunityLikeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommunityLikeRepositoryImpl implements CommunityLikeRepository {

    private final CommunityLikeJpaRepository jpaRepository;
//    private final CommunityLikeQueryRepository queryRepository;

    @Override
    public Optional<CommunityLike> findByCommunityIdAndMemberId(final Long communityId, final Long memberId) {
        return jpaRepository.findByCommunityIdAndMemberId(communityId, memberId);
    }

    @Override
    public boolean existsByCommunityIdAndMemberId(final Long postId, final Long memberId) {
        return jpaRepository.existsByCommunityIdAndMemberId(postId, memberId);
    }

    @Override
    public void deleteById(final Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Long countCommunityLikesByCommunityId(final Long communityId) {
//        return queryRepository.countCommunityLikesByCommunityId(communityId);
        return null;
    }

    @Override
    public CommunityLike save(final CommunityLike communityLike) {
        return jpaRepository.save(communityLike);
    }
}
