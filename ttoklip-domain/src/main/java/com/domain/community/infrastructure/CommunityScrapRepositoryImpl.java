package com.domain.community.infrastructure;

import com.domain.community.domain.CommunityScrap;
import com.domain.community.domain.CommunityScrapRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommunityScrapRepositoryImpl implements CommunityScrapRepository {

    private final CommunityScrapJpaRepository jpaRepository;
    private final CommunityScrapQueryRepository queryRepository;

    @Override
    public Optional<CommunityScrap> findByCommunityIdAndMemberId(final Long communityId, final Long memberId) {
        return jpaRepository.findByCommunityIdAndMemberId(communityId, memberId);
    }

    @Override
    public boolean existsByCommunityIdAndMemberId(final Long postId, final Long memberId) {
        return jpaRepository.existsByCommunityIdAndMemberId(postId, memberId);
    }

    @Override
    public CommunityScrap save(final CommunityScrap communityScrap) {
        return jpaRepository.save(communityScrap);
    }

    @Override
    public void deleteById(final Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Long countCommunityScrapsByCommunityId(final Long communityId) {
        return queryRepository.countCommunityScrapsByCommunityId(communityId);
    }
}
