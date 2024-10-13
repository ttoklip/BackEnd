package com.api.ttoklip.domain.honeytip.repository.image;

import com.api.ttoklip.domain.honeytip.domain.HoneyTipImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoneyTipImageJpaRepository extends JpaRepository<HoneyTipImage, Long> {

    boolean existsByHoneyTipIdAndUrl(final Long honeyTipId, final String url);
}
