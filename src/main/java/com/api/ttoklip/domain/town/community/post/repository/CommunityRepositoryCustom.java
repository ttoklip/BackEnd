package com.api.ttoklip.domain.town.community.post.repository;

import com.api.ttoklip.domain.town.community.post.entity.Community;

public interface CommunityRepositoryCustom {
    Community findByIdUndeleted(final Long communityId);
}