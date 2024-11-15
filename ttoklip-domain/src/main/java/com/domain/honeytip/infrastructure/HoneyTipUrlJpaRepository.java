package com.domain.honeytip.infrastructure;

import com.domain.honeytip.domain.HoneyTipUrl;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoneyTipUrlJpaRepository extends JpaRepository<HoneyTipUrl, Long> {
    List<HoneyTipUrl> findByHoneyTipId(Long honeyTipId);
}
