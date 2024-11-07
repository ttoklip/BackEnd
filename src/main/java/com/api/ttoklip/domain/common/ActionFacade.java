package com.api.ttoklip.domain.common;

import com.api.ttoklip.global.success.Message;

public interface ActionFacade {
    Message register(Long postId, final Long currentMemberId);

    Message cancel(Long postId, final Long currentMemberId);
}
