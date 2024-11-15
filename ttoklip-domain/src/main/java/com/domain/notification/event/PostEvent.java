package com.domain.notification.event;

import com.common.NotiCategory;

public record PostEvent(Long postId, NotiCategory notiCategory, Long fromMemberId) {

    public static Object of(final Long targetIndex, final NotiCategory notiCategory, final Long fromMemberId) {
        return new PostEvent(targetIndex, notiCategory, fromMemberId);
    }
}
