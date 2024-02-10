package com.api.ttoklip.domain.town.community.like.repository;

import com.api.ttoklip.domain.town.community.like.entity.CommunityLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityLikeRepository extends JpaRepository<CommunityLike, Long>, LikeCustomRepositoty {

}
