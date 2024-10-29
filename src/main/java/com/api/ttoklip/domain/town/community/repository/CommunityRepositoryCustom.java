package com.api.ttoklip.domain.town.community.repository;

import com.api.ttoklip.domain.town.TownCriteria;
import com.api.ttoklip.domain.town.community.domain.CommunityComment;
import com.api.ttoklip.domain.town.community.domain.Community;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommunityRepositoryCustom {

    Community findByIdActivated(final Long communityId);

    Community findByIdFetchJoin(final Long postId);

    List<CommunityComment> findActiveCommentsByCommunityId(final Long postId);

    List<Community> getRecent3(final TownCriteria townCriteria);

    Page<Community> getPaging(final TownCriteria townCriteria, final Pageable pageable);
}
