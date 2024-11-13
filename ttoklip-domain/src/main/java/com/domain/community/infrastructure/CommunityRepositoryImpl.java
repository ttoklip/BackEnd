package com.domain.community.infrastructure;

import com.domain.common.vo.TownCriteria;
import com.domain.community.domain.Community;
import com.domain.community.domain.CommunityComment;
import com.domain.community.domain.CommunityRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityRepository {

    private final CommunityQueryRepository queryRepository;
    private final CommunityJpaRepository jpaRepository;

    @Override
    public Community findByIdActivated(final Long communityId) {
        return queryRepository.findByIdActivated(communityId);
    }

    @Override
    public Community findByIdFetchJoin(final Long postId) {
        return queryRepository.findByIdFetchJoin(postId);
    }

    @Override
    public List<CommunityComment> findActiveCommentsByCommunityId(final Long postId) {
        return queryRepository.findActiveCommentsByCommunityId(postId);
    }

    @Override
    public List<Community> getRecent3(final TownCriteria townCriteria) {
        return queryRepository.getRecent3(townCriteria);
    }

    @Override
    public Page<Community> getPaging(final TownCriteria townCriteria, final Pageable pageable) {
        return queryRepository.getPaging(townCriteria, pageable);
    }

    @Override
    public Community save(final Community community) {
        return jpaRepository.save(community);
    }
}
