package com.api.ttoklip.domain.honeytip.repository.like;

import com.api.ttoklip.domain.honeytip.domain.HoneyTipLike;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HoneyTipLikeRepositoryImpl implements HoneyTipLikeRepository {

    private final HoneyTipLikeJpaRepository honeyTipLikeJpaRepository;
    private final HoneyTipLikeQueryRepository honeyTipLikeQueryRepository;

    @Override
    public Optional<HoneyTipLike> findByHoneyTipIdAndMemberId(final Long honeyTipId, final Long memberId) {
        return honeyTipLikeJpaRepository.findByHoneyTipIdAndMemberId(honeyTipId, memberId);
    }

    @Override
    public boolean existsByHoneyTipIdAndMemberId(final Long honeyTipId, final Long memberId) {
        return honeyTipLikeJpaRepository.existsByHoneyTipIdAndMemberId(honeyTipId, memberId);
    }

    @Override
    public Long countHoneyTipLikesByHoneyTipId(final Long postId) {
        return honeyTipLikeQueryRepository.countHoneyTipLikesByHoneyTipId(postId);
    }

    @Override
    public void save(final HoneyTipLike honeyTipLike) {
        honeyTipLikeJpaRepository.save(honeyTipLike);
    }

    @Override
    public void deleteById(final Long id) {
        honeyTipLikeJpaRepository.deleteById(id);
    }
}
