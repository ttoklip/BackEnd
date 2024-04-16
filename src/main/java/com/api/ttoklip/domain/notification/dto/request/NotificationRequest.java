package com.api.ttoklip.domain.notification.dto.request;

public record NotificationRequest(Long targetMemberId, String className, String methodName) {

    public static NotificationRequest of(final Long targetMemberId, final String className, final String methodName) {
        return new NotificationRequest(targetMemberId, className, methodName);
    }
}
