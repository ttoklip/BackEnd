package com.api.ttoklip.domain.honeytip.repository.like;

import com.api.ttoklip.domain.honeytip.domain.HoneyTipLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoneyTipLikeRepository extends JpaRepository<HoneyTipLike, Long>, HoneyTipLikeRepositoryCustom {
    Optional<HoneyTipLike> findByHoneyTipIdAndMemberId(Long honeyTipId, Long memberId);

    boolean existsByHoneyTipIdAndMemberId(Long honeyTipId, Long memberId);
}
