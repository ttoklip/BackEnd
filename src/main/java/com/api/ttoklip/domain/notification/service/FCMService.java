package com.api.ttoklip.domain.notification.service;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.notification.entity.NotiCategory;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class FCMService {

    private static final String TOPIC_DATA_NAME = "topic";
    private static final String TEXT_DATA_NAME = "text";
    private static final String TITLE_DATA_NAME = "title";

    private final NotificationService notificationService;

    public void sendNotification(
            final NotiCategory categoryName,
            final Member member
    ) {
        try {
            Message message = Message.builder()
                    .putData(TOPIC_DATA_NAME, categoryName.name())
                    .putData(TITLE_DATA_NAME, categoryName.getTitle())
                    .putData(TEXT_DATA_NAME, categoryName.getText())
                    .setToken(member.getFcmToken())
                    .build();
            FirebaseMessaging.getInstance()
                    .send(message);
            notificationService.register(categoryName, member, true);
        } catch (FirebaseMessagingException e) {
            log.info("FCMService.sendNotification + FirebaseMessagingException");
            notificationService.register(categoryName, member, false);
            throw new ApiException(ErrorType._NOT_SEND_ABLE);
        } catch (IllegalArgumentException e) {
            log.info("FCMService.sendNotification + IllegalArgumentException");
            notificationService.register(categoryName, member, false);
        }
    }
}
