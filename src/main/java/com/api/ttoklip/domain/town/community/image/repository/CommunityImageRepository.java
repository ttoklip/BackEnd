package com.api.ttoklip.domain.town.community.image.repository;

import com.api.ttoklip.domain.town.community.image.entity.CommunityImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityImageRepository extends JpaRepository<CommunityImage, Long> {
    void deleteAllByCommunityId(final Long communityId);
}
