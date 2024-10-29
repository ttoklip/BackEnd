package com.api.ttoklip.domain.town.community.repository;

import com.api.ttoklip.domain.town.community.domain.CommunityImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityImageRepository extends JpaRepository<CommunityImage, Long>, CommunityImageRepositoryCustom {
}
