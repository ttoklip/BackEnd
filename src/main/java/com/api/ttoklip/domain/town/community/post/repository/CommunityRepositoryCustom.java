package com.api.ttoklip.domain.town.community.post.repository;

import com.api.ttoklip.domain.town.community.comment.CommunityComment;
import com.api.ttoklip.domain.town.community.post.entity.Community;

import java.util.List;

public interface CommunityRepositoryCustom {
    Community findByIdFetchJoin(final Long questionPostId);
    List<CommunityComment> findActiveCommentsByCommunityId(final Long commentId);
}
