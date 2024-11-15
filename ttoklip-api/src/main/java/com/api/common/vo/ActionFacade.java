package com.api.common.vo;

import com.api.global.support.response.Message;

public interface ActionFacade {
    Message register(Long postId, final Long currentMemberId);

    Message cancel(Long postId, final Long currentMemberId);
}
