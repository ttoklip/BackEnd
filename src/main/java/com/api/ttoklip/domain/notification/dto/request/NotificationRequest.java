package com.api.ttoklip.domain.notification.dto.request;

public record NotificationRequest(Long targetIndex, String className, String methodName) {

    public static NotificationRequest of(final Long targetIndex, final String className, final String methodName) {
        return new NotificationRequest(targetIndex, className, methodName);
    }
}
