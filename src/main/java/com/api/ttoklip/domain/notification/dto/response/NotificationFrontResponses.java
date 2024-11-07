package com.api.ttoklip.domain.notification.dto.response;

import java.util.List;

public class NotificationFrontResponses {

    private final List<NotificationFrontResponse> responses;

    private NotificationFrontResponses(final List<NotificationFrontResponse> responses) {
        this.responses = responses;
    }

    public static NotificationFrontResponses from(final List<NotificationFrontResponse> notificationFrontResponses) {
        return new NotificationFrontResponses(notificationFrontResponses);
    }

    public List<NotificationFrontResponse> getResponses() {
        return responses;
    }
}
