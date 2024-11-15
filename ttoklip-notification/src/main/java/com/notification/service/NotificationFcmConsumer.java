package com.notification.service;

import com.common.NotiCategory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationFcmConsumer {

    private final FCMService fcmService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka.topic.notification}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message, Acknowledgment acknowledgment) {
        try {
            NotificationMessage notificationMessage = objectMapper.readValue(message, NotificationMessage.class);
            fcmService.sendNotification(notificationMessage.notiCategory(), notificationMessage.fcmToken());

            log.info("FCM 알림 전송 성공: {}", message);

            acknowledgment.acknowledge();
        } catch (Exception e) {
            // ToDo DLQ, Batch 처리 고민. 엔티티 추가?
            log.error("Kafka 메시지 처리 오류: {}", e.getMessage());
        }
    }

    private record NotificationMessage(
            NotiCategory notiCategory, Long targetMemberId,
            Long targetClassId, String fcmToken
    ) { }
}
