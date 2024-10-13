package com.api.ttoklip.domain.honeytip.repository.url;

import com.api.ttoklip.domain.honeytip.domain.HoneyTipUrl;
import java.util.List;

public interface HoneyTipUrlRepository {

    List<HoneyTipUrl> findByHoneyTipId(Long honeyTipId);
    void deleteAllByIds(final List<Long> ids);

    void save(HoneyTipUrl honeyTipUrl);

    void saveAll(List<HoneyTipUrl> honeyTipUrlsToSave);
}
