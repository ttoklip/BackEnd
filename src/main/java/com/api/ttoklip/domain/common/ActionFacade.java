package com.api.ttoklip.domain.common;

import com.api.ttoklip.global.success.Message;

public interface ActionFacade {
    Message register(Long postId);

    Message cancel(Long postId);
}
