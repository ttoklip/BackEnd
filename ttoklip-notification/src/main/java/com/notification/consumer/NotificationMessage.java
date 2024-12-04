package com.notification.consumer;

import com.common.NotiCategory;

public record NotificationMessage(NotiCategory notiCategory, Long targetMemberId,
                                  Long targetClassId, String fcmToken) {
}
