package com.api.ttoklip.domain.honeytip.repository.url;

import com.api.ttoklip.domain.honeytip.domain.HoneyTipUrl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HoneyTipUrlRepositoryImpl implements HoneyTipUrlRepository {

    private final HoneyTipUrlJpaRepository honeyTipUrlJpaRepository;
    private final HoneyTipUrlQueryRepository honeyTipUrlQueryRepository;


    @Override
    public List<HoneyTipUrl> findByHoneyTipId(final Long honeyTipId) {
        return honeyTipUrlJpaRepository.findByHoneyTipId(honeyTipId);
    }

    @Override
    public void deleteAllByIds(final List<Long> ids) {
        honeyTipUrlQueryRepository.deleteAllByIds(ids);
    }

    @Override
    public void save(final HoneyTipUrl honeyTipUrl) {
        honeyTipUrlJpaRepository.save(honeyTipUrl);
    }

    @Override
    public void saveAll(final List<HoneyTipUrl> honeyTipUrlsToSave) {
        honeyTipUrlJpaRepository.saveAll(honeyTipUrlsToSave);
    }
}
