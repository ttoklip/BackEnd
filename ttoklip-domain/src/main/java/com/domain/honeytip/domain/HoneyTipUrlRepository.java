package com.domain.honeytip.domain;

import java.util.List;

public interface HoneyTipUrlRepository {

    List<HoneyTipUrl> findByHoneyTipId(Long honeyTipId);

    void deleteAllByIds(List<Long> ids);

    void save(HoneyTipUrl honeyTipUrl);

    void saveAll(List<HoneyTipUrl> honeyTipUrlsToSave);
}
