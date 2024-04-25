package com.api.ttoklip.domain.notification.dto.response;

public record NotificationServerResponse(Long targetMemberId, Long targetClassId) {

    public static NotificationServerResponse of(Long targetMemberId, Long targetClassId) {
        return new NotificationServerResponse(targetMemberId, targetClassId);
    }
}
