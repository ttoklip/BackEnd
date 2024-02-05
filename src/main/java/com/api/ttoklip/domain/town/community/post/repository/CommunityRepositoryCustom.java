package com.api.ttoklip.domain.town.community.post.repository;

import com.api.ttoklip.domain.town.community.comment.CommunityComment;
import com.api.ttoklip.domain.town.community.post.entity.Community;

import java.util.List;

public interface CommunityRepositoryCustom {

    Community findByIdActivated(final Long communityId);

    Community findByIdFetchJoin(final Long postId);

    List<CommunityComment> findActiveCommentsByCommunityId(final Long postId);
}
