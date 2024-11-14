package com.domain.honeytip.repository;

import com.domain.honeytip.domain.HoneyTip;
import com.domain.honeytip.domain.HoneyTipScrap;
import com.domain.honeytip.domain.HoneyTipScrapRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
    public Page<HoneyTip> getScrapPaging(final Long memberId, final Pageable pageable) {
        List<HoneyTip> filtered = honeyTipScrapRepository.values().stream()
                .filter(honeyTipScrap -> honeyTipScrap.getMember().getId().equals(memberId))
                .map(HoneyTipScrap::getHoneyTip)
                .filter(honeyTip -> !honeyTip.isDeleted())
                .sorted((h1, h2) -> h2.getId().compareTo(h1.getId())) // 최신순 정렬
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filtered.size());
        List<HoneyTip> pageContent = filtered.subList(start, end);

        return new PageImpl<>(pageContent, pageable, filtered.size());
    }


    @Override
    public void deleteById(final Long id) {
        honeyTipScrapRepository.remove(id);
    }
}
