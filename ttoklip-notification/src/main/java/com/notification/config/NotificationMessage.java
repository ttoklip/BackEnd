package com.notification.config;

import com.common.NotiCategory;

public record NotificationMessage(NotiCategory notiCategory, Long targetMemberId,
                                  Long targetClassId, String fcmToken) {
}
