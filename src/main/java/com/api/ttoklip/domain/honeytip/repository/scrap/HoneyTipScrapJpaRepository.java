package com.api.ttoklip.domain.honeytip.repository.scrap;

import com.api.ttoklip.domain.honeytip.domain.HoneyTipScrap;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoneyTipScrapJpaRepository extends JpaRepository<HoneyTipScrap, Long> {

    Optional<HoneyTipScrap> findByHoneyTipIdAndMemberId(Long honeyTipId, Long memberId);

    boolean existsByHoneyTipIdAndMemberId(Long honeyTipId, Long memberId);
}

