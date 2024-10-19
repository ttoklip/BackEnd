package com.domain.honeytip.repository;

import com.api.ttoklip.domain.honeytip.domain.HoneyTipUrl;
import com.api.ttoklip.domain.honeytip.repository.url.HoneyTipUrlRepository;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FakeHoneyTipUrlRepository implements HoneyTipUrlRepository {

    private final HashMap<Long, HoneyTipUrl> honeyTipUrlRepository = new HashMap<>();
    private Long honeyTipUrlId = 0L;

    @Override
    public List<HoneyTipUrl> findByHoneyTipId(final Long honeyTipId) {
        return honeyTipUrlRepository.values().stream()
                .filter(honeyTipUrl -> honeyTipUrl.getHoneyTip().getId().equals(honeyTipId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllByIds(final List<Long> ids) {
        ids.forEach(honeyTipUrlRepository::remove);
    }

    @Override
    public void save(final HoneyTipUrl honeyTipUrl) {
        honeyTipUrlId++;
        HoneyTipUrl savedHoneyTipUrl = HoneyTipUrl.builder()
                .id(honeyTipUrlId)
                .honeyTip(honeyTipUrl.getHoneyTip())
                .url(honeyTipUrl.getUrl())
                .build();

        honeyTipUrlRepository.put(honeyTipUrlId, savedHoneyTipUrl);
    }

    @Override
    public void saveAll(final List<HoneyTipUrl> honeyTipUrlsToSave) {
        HashMap<Long, HoneyTipUrl> backup = new HashMap<>(honeyTipUrlRepository);

        try {
            honeyTipUrlsToSave.forEach(this::save);
        } catch (Exception e) {
            // 예외 발생 시 롤백
            honeyTipUrlRepository.clear();
            honeyTipUrlRepository.putAll(backup);

            throw new RuntimeException("저장 도중 예외 발생", e);
        }
    }


}
