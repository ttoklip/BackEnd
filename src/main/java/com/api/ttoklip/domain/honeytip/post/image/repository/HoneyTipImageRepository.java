package com.api.ttoklip.domain.honeytip.post.image.repository;

import com.api.ttoklip.domain.honeytip.post.image.domain.HoneyTipImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoneyTipImageRepository extends JpaRepository<HoneyTipImage, Long> {
}
