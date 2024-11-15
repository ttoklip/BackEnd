package com.domain.community.infrastructure;

import com.domain.community.domain.CommunityImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityImageJpaRepository extends JpaRepository<CommunityImage, Long> {
}
