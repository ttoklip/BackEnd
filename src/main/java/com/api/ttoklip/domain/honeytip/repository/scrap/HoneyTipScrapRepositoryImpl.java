package com.api.ttoklip.domain.honeytip.repository.scrap;

import com.api.ttoklip.domain.honeytip.domain.HoneyTipScrap;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HoneyTipScrapRepositoryImpl implements HoneyTipScrapRepository {

    private final HoneyTipScrapJpaRepository honeyTipScrapJpaRepository;
    private final HoneyTipScrapQueryRepository honeyTipScrapQueryRepository;

    @Override
    public Optional<HoneyTipScrap> findByHoneyTipIdAndMemberId(final Long honeyTipId, final Long memberId) {
        return honeyTipScrapJpaRepository.findByHoneyTipIdAndMemberId(honeyTipId, memberId);
    }

    @Override
    public boolean existsByHoneyTipIdAndMemberId(final Long honeyTipId, final Long memberId) {
        return honeyTipScrapJpaRepository.existsByHoneyTipIdAndMemberId(honeyTipId, memberId);
    }

    @Override
    public Long countHoneyTipScrapsByHoneyTipId(final Long postId) {
        return honeyTipScrapQueryRepository.countHoneyTipScrapByHoneyTipId(postId);
    }

    @Override
    public void deleteById(final Long id) {
        honeyTipScrapJpaRepository.deleteById(id);
    }

    @Override
    public void save(final HoneyTipScrap honeyTipScrap) {
        honeyTipScrapJpaRepository.save(honeyTipScrap);
    }
}
