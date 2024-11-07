package com.api.ttoklip.domain.town.community.repository.like;

import com.api.ttoklip.domain.town.community.domain.CommunityLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommunityLikeRepositoryImpl implements CommunityLikeRepository {

    private final CommunityLikeJpaRepository jpaRepository;
    private final CommunityLikeQueryRepository queryRepository;

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
        return queryRepository.countCommunityLikesByCommunityId(communityId);
    }

    @Override
    public CommunityLike save(final CommunityLike communityLike) {
        return jpaRepository.save(communityLike);
    }
}
