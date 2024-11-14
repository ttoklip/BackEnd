package com.domain.notification.dto.request;

import com.domain.notification.domain.vo.NotiCategory;

public record NotificationRequest(Long targetIndex, NotiCategory notiCategory, Long fromMemberId) {

    public static NotificationRequest of(final Long targetIndex, final NotiCategory notiCategory, final Long fromMemberId) {
        return new NotificationRequest(targetIndex, notiCategory, fromMemberId);
    }
}
