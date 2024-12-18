package com.notification.consumer;

import com.notification.service.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionConsumer {

    private final SlackService slackService;

    @KafkaListener(
            topics = "${kafka.topic.internal.error}",
            groupId = "${spring.kafka.consumer.group-id-exception}",
            containerFactory = "kafkaListenerContainerFactoryException"
    )
//    public void listen(ErrorMessage errorMessage, Acknowledgment acknowledgment) {
//        try {
//            slackNotificationService.sendErrorMessage(errorMessage);
//            log.info("예외 알림 전송 성공: {}", errorMessage);
//            acknowledgment.acknowledge();
//        } catch (Exception e) {
//            log.error("Kafka 메시지 처리 오류: {}", e.getMessage());
//        }
//    }
    public void listen(String message) {
        try {
            slackService.sendErrorMessageToSlack(message);
            log.info("예외 알림 전송 성공: {}", message);
//            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Kafka 메시지 처리 오류: {}", e.getMessage());
        }
    }


}
