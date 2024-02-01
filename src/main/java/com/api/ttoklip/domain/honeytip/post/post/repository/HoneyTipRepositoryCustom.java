package com.api.ttoklip.domain.honeytip.post.post.repository;

import com.api.ttoklip.domain.honeytip.post.post.domain.HoneyTip;

public interface HoneyTipRepositoryCustom {
    HoneyTip findByIdActivated(final Long honeyTipId);
}
