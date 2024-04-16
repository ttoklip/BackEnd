package com.api.ttoklip.domain.notification.service;

import com.api.ttoklip.domain.notification.entity.NotiCategory;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FCMService {

    private static final String TEXT_DATA_NAME = "text";
    private static final String TITLE_DATA_NAME = "title";

    public void sendNotification(
            final NotiCategory categoryName,
            final String fcmToken
    ) {
        try {
            FirebaseMessaging.getInstance()
                    .send(
                            Message.builder()
                                    .putData(TITLE_DATA_NAME, categoryName.getTitle())
                                    .putData(TEXT_DATA_NAME, categoryName.getText())
                                    .setToken(fcmToken)
                                    .build()
                    );
        } catch (FirebaseMessagingException e) {
            throw new ApiException(ErrorType._NOT_SEND_ABLE);
        }
    }
}
