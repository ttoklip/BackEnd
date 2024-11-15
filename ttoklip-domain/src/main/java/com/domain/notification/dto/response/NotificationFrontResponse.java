package com.domain.notification.dto.response;

public record NotificationFrontResponse(Long notificationId, Long targetClassId, String targetClassName, String title,
                                        String text, boolean noticeSuccess) {

    public static NotificationFrontResponse of(Long notificationId, Long targetClassId, String targetClassName,
                                               String title,
                                               String text, boolean noticeSuccess) {
        return new NotificationFrontResponse(notificationId, targetClassId, targetClassName, title, text,
                noticeSuccess);
    }
}
