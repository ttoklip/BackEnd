package com.api.ttoklip.domain.town.community.post.repository;

import com.api.ttoklip.domain.town.community.comment.CommunityComment;
import com.api.ttoklip.domain.town.community.post.entity.Community;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommunityRepositoryCustom {

    Community findByIdActivated(final Long communityId);

    Community findByIdFetchJoin(final Long postId);

    List<CommunityComment> findActiveCommentsByCommunityId(final Long postId);

    List<Community> getRecent3();

    Page<Community> getPaging(Pageable pageable);
}
