package com.api.ttoklip.domain.honeytip.repository.scrap;

import com.api.ttoklip.domain.honeytip.domain.HoneyTipScrap;
import java.util.Optional;

public interface HoneyTipScrapRepository {
    Optional<HoneyTipScrap> findByHoneyTipIdAndMemberId(Long honeyTipId, Long memberId);

    boolean existsByHoneyTipIdAndMemberId(Long honeyTipId, Long memberId);

    Long countHoneyTipScrapsByHoneyTipId(Long postId);

    void deleteById(Long id);

    void save(HoneyTipScrap honeyTipScrap);
}
