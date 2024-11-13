package com.domain.community.domain;

import com.domain.common.vo.TownCriteria;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommunityRepository {

    Community findByIdActivated(final Long communityId);

    Community findByIdFetchJoin(final Long postId);

    List<CommunityComment> findActiveCommentsByCommunityId(final Long postId);

    List<Community> getRecent3(final TownCriteria townCriteria);

    Page<Community> getPaging(final TownCriteria townCriteria, final Pageable pageable);

    Community save(Community community);

    Page<Community> getContain(final String keyword, final Pageable pageable, final String sort);

    Page<Community> getMatchWriterPaging(Long memberId, Pageable pageable);
}
