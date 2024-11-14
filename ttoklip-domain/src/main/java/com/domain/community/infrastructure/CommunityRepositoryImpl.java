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

    private final CommunityJpaRepository jpaRepository;
//    private final CommunityQueryRepository queryDSLRepository;

    @Override
    public Community findByIdActivated(final Long communityId) {
//        return queryDSLRepository.findByIdActivated(communityId);
        return null;
    }

    @Override
    public Community findByIdFetchJoin(final Long postId) {
//        return queryDSLRepository.findByIdFetchJoin(postId);
        return null;
    }

    @Override
    public List<CommunityComment> findActiveCommentsByCommunityId(final Long postId) {
//        return queryDSLRepository.findActiveCommentsByCommunityId(postId);
        return null;
    }

    @Override
    public List<Community> getRecent3(final TownCriteria townCriteria) {
//        return queryDSLRepository.getRecent3(townCriteria);
        return null;
    }

    @Override
    public Page<Community> getPaging(final TownCriteria townCriteria, final Pageable pageable) {
//        return queryDSLRepository.getPaging(townCriteria, pageable);
        return null;
    }

    @Override
    public Community save(final Community community) {
        return jpaRepository.save(community);
    }

    @Override
    public Page<Community> getContain(final String keyword, final Pageable pageable, final String sort) {
//        return queryDSLRepository.getContain(keyword, pageable, sort);
        return null;
    }

    @Override
    public Page<Community> getMatchWriterPaging(final Long memberId, final Pageable pageable) {
//        return queryDSLRepository.getMatchWriterPaging(memberId, pageable);
        return null;
    }
}
