package com.domain.honeytip.infrastructure;

import com.domain.honeytip.domain.HoneyTip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoneyTipJpaRepository extends JpaRepository<HoneyTip, Long> {
}
