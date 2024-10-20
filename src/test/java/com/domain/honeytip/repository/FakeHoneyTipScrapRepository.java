package com.domain.honeytip.repository;

import com.api.ttoklip.domain.honeytip.domain.HoneyTipScrap;
import com.api.ttoklip.domain.honeytip.repository.scrap.HoneyTipScrapRepository;
import java.util.HashMap;
import java.util.Optional;

public class FakeHoneyTipScrapRepository implements HoneyTipScrapRepository {

    private final HashMap<Long, HoneyTipScrap> honeyTipScrapRepository = new HashMap<>();
    private Long honeyTipScrapId = 0L;

    @Override
    public Optional<HoneyTipScrap> findByHoneyTipIdAndMemberId(final Long honeyTipId, final Long memberId) {
        return honeyTipScrapRepository.values().stream()
                .filter(honeyTipScrap -> honeyTipScrap.getHoneyTip().getId().equals(honeyTipId)
                        && honeyTipScrap.getMember().getId().equals(memberId))
                .findFirst();
    }

    @Override
    public boolean existsByHoneyTipIdAndMemberId(final Long honeyTipId, final Long memberId) {
        return honeyTipScrapRepository.values().stream()
                .anyMatch(honeyTipScrap -> honeyTipScrap.getHoneyTip().getId().equals(honeyTipId)
                        && honeyTipScrap.getMember().getId().equals(memberId));
    }

    @Override
    public Long countHoneyTipScrapsByHoneyTipId(final Long honeyTipId) {
        return honeyTipScrapRepository.values().stream()
                .filter(honeyTipScrap -> honeyTipScrap.getHoneyTip().getId().equals(honeyTipId))
                .count();
    }

    @Override
    public void save(final HoneyTipScrap honeyTipScrap) {
        honeyTipScrapId++;
        HoneyTipScrap savedHoneyTipScrap = HoneyTipScrap.builder()
                .id(honeyTipScrapId)
                .honeyTip(honeyTipScrap.getHoneyTip())
                .member(honeyTipScrap.getMember())
                .build();

        honeyTipScrapRepository.put(honeyTipScrapId, savedHoneyTipScrap);
    }

    @Override
    public void deleteById(final Long id) {
        honeyTipScrapRepository.remove(id);
    }
}
