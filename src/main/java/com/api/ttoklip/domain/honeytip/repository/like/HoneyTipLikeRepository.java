package com.api.ttoklip.domain.honeytip.repository.like;

import com.api.ttoklip.domain.honeytip.domain.HoneyTipLike;
import java.util.Optional;

public interface HoneyTipLikeRepository {

    Optional<HoneyTipLike> findByHoneyTipIdAndMemberId(Long honeyTipId, Long memberId);

    boolean existsByHoneyTipIdAndMemberId(Long honeyTipId, Long memberId);

    Long countHoneyTipLikesByHoneyTipId(Long postId);

    void save(HoneyTipLike honeyTipLike);

    void deleteById(Long id);
}
