package com.domain.notification.dto.response;

public record NotificationInternalResponse(Long targetMemberId, Long targetClassId) {

    public static NotificationInternalResponse of(Long targetMemberId, Long targetClassId) {
        return new NotificationInternalResponse(targetMemberId, targetClassId);
    }
}
