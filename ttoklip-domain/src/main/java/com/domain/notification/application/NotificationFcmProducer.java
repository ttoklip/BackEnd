package com.domain.notification.application;

import com.common.NotiCategory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationFcmProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.notification}")
    private String topicName;

    public void sendNotificationMessage(NotiCategory notiCategory, Long targetMemberId, Long targetClassId, String fcmToken) {
        try {
            String message = createJsonMessage(notiCategory, targetMemberId, targetClassId, fcmToken);
            kafkaTemplate.send(topicName, message);
            log.info("Kafka 메시지 전송: {}", message);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    private String createJsonMessage(
            NotiCategory notiCategory, Long targetMemberId, Long targetClassId, String fcmToken
    ) throws JsonProcessingException {
        Message message = new Message(notiCategory, targetMemberId, targetClassId, fcmToken);
        return objectMapper.writeValueAsString(message);
    }

    private record Message(
            NotiCategory notiCategory,
            Long targetMemberId,
            Long targetClassId,
            String fcmToken
    ) { }
}
