package com.domain.community.infrastructure;

import com.domain.community.domain.Community;
import com.domain.community.domain.CommunityScrap;
import com.domain.community.domain.CommunityScrapRepository;
import com.querydsl.core.types.dsl.Wildcard;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommunityScrapRepositoryImpl implements CommunityScrapRepository {

    private final CommunityScrapJpaRepository jpaRepository;
//    private final CommunityScrapQueryRepository queryRepository;

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
//        return queryRepository.countCommunityScrapsByCommunityId(communityId);
        return null;
    }

    @Override
    public Page<Community> getScrapPaging(final Long memberId, final Pageable pageable) {
//        return queryRepository.getScrapPaging(memberId, pageable);
        return null;
    }


}
