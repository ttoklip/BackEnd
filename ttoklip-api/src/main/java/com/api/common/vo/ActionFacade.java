package com.api.common.vo;

import com.api.global.success.Message;

public interface ActionFacade {
    Message register(Long postId, final Long currentMemberId);

    Message cancel(Long postId, final Long currentMemberId);
}
