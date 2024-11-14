package com.infrastructure.notification.service;

import com.domain.member.domain.Member;
import com.domain.notification.domain.vo.NotiCategory;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FCMService {

    private static final String TOPIC_DATA_NAME = "topic";
    private static final String TEXT_DATA_NAME = "text";
    private static final String TITLE_DATA_NAME = "title";

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

            FirebaseMessaging.getInstance().send(message);


        } catch (FirebaseMessagingException e) {
            log.info("FCMService.sendNotification + FirebaseMessagingException");
            e.printStackTrace();
            throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
