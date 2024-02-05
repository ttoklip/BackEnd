package com.api.ttoklip.domain.town.community.post.repository;

import com.api.ttoklip.domain.town.community.post.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long>, CommunityRepositoryCustom {

    Community findByIdUnDeleted(final Long postId);

}
