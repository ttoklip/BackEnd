package com.api.ttoklip.domain.honeytip.url.repository;

import java.util.List;

public interface HoneyTipUrlRepositoryCustom {
    void deleteAllByIds(final List<Long> ids);
}
