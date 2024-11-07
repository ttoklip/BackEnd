package com.api.ttoklip.domain.honeytip.repository.url;

import com.api.ttoklip.domain.honeytip.domain.HoneyTipUrl;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoneyTipUrlJpaRepository extends JpaRepository<HoneyTipUrl, Long> {
    List<HoneyTipUrl> findByHoneyTipId(Long honeyTipId);
}
