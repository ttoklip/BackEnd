package com.api.ttoklip.domain.honeytip.post.repository;

import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;

public interface HoneyTipRepositoryCustom {
    HoneyTip findByIdActivated(final Long honeyTipId);
}
