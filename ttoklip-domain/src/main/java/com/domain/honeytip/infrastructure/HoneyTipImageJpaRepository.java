package com.domain.honeytip.infrastructure;

import com.domain.honeytip.domain.HoneyTipImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoneyTipImageJpaRepository extends JpaRepository<HoneyTipImage, Long> {

    boolean existsByHoneyTipIdAndUrl(Long honeyTipId, String url);
}
