package com.domain.notification.dto.response;

import java.util.List;

public record NotificationFrontResponses(List<NotificationFrontResponse> responses) {

    public static NotificationFrontResponses from(final List<NotificationFrontResponse> notificationFrontResponses) {
        return new NotificationFrontResponses(notificationFrontResponses);
    }
}
