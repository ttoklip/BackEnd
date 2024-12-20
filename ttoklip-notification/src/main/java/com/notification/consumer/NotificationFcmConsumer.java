package com.notification.consumer;

import com.notification.service.FCMService;
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

    @KafkaListener(
            topics = "${kafka.topic.notification}",
            groupId = "${spring.kafka.consumer.group-id-fcm}",
            containerFactory = "kafkaListenerContainerFactoryFcm"
    )
    public void listen(NotificationMessage notificationMessage, Acknowledgment acknowledgment) {
        try {
            fcmService.sendNotification(notificationMessage.notiCategory(), notificationMessage.fcmToken());
            log.info("FCM 알림 전송 성공: {}", notificationMessage);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            // ToDo DLQ, Batch 처리 고민. Cache? 엔티티 추가?
            log.error("Kafka 메시지 처리 오류: {}", e.getMessage());
        }
    }

}
