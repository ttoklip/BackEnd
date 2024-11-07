package com.domain.honeytip.repository;

import com.api.ttoklip.domain.honeytip.domain.HoneyTipLike;
import com.api.ttoklip.domain.honeytip.repository.like.HoneyTipLikeRepository;
import java.util.HashMap;
import java.util.Optional;

public class FakeHoneyTipLikeRepository implements HoneyTipLikeRepository {

    private final HashMap<Long, HoneyTipLike> honeyTipLikeRepository = new HashMap<>();
    private Long honeyTipLikeId = 0L;

    @Override
    public Optional<HoneyTipLike> findByHoneyTipIdAndMemberId(final Long honeyTipId, final Long memberId) {
        return honeyTipLikeRepository.values().stream()
                .filter(honeyTipLike -> honeyTipLike.getHoneyTip().getId().equals(honeyTipId)
                        && honeyTipLike.getMember().getId().equals(memberId))
                .findFirst();
    }

    @Override
    public boolean existsByHoneyTipIdAndMemberId(final Long honeyTipId, final Long memberId) {
        return honeyTipLikeRepository.values().stream()
                .anyMatch(honeyTipLike -> honeyTipLike.getHoneyTip().getId().equals(honeyTipId)
                        && honeyTipLike.getMember().getId().equals(memberId));
    }

    @Override
    public Long countHoneyTipLikesByHoneyTipId(final Long honeyTipId) {
        return honeyTipLikeRepository.values().stream()
                .filter(honeyTipLike -> honeyTipLike.getHoneyTip().getId().equals(honeyTipId))
                .count();
    }

    @Override
    public void save(final HoneyTipLike honeyTipLike) {
        honeyTipLikeId++;
        HoneyTipLike savedHoneyTipLike = HoneyTipLike.builder()
                .id(honeyTipLikeId)
                .honeyTip(honeyTipLike.getHoneyTip())
                .member(honeyTipLike.getMember())
                .build();

        honeyTipLikeRepository.put(honeyTipLikeId, savedHoneyTipLike);
    }

    @Override
    public void deleteById(final Long id) {
        honeyTipLikeRepository.remove(id);
    }
}
