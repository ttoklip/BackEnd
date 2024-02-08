package com.api.ttoklip.domain.town.community.like.repository;

import com.api.ttoklip.domain.town.community.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long>, LikeCustomRepositoty {

}
