package com.api.ttoklip.domain.town.community.image.repository;

import com.api.ttoklip.domain.town.community.image.entity.CommunityImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<CommunityImage, Long> {
}
