package com.domain.exception.application;

import com.common.event.AsyncInternalServerExceptionEvent;
import com.common.event.InternalServerExceptionEvent;
import com.common.event.Modules;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InternalErrorService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.internal.error.sync}")
    private String internalErrorTopic;

    @Value("${kafka.topic.internal.error.async}")
    private String asyncInternalErrorTopic;

    @EventListener(InternalServerExceptionEvent.class)
    public void handleInternalServerErrorEvent(InternalServerExceptionEvent event) {
        try {
            SyncMessage syncMessage = new SyncMessage(
                    event.getErrorTime(),
                    event.getModules(),
                    event.getThrowable()
            );
            String jsonMessage = objectMapper.writeValueAsString(syncMessage);
            kafkaTemplate.send(internalErrorTopic, jsonMessage);
            log.info("InternalServerErrorEvent Kafka 메시지 전송: {}", jsonMessage);
        } catch (JsonProcessingException e) {
            log.error("JSON 직렬화 오류: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Kafka 전송 오류: {}", e.getMessage());
        }
    }

    @EventListener(AsyncInternalServerExceptionEvent.class)
    public void handleAsyncInternalServerErrorEvent(AsyncInternalServerExceptionEvent event) {
        try {
            AsyncMessage asyncMessage = new AsyncMessage(
                    event.getErrorTime(),
                    event.getModules(),
                    event.getThrowable()
            );
            String jsonMessage = objectMapper.writeValueAsString(asyncMessage);
            kafkaTemplate.send(asyncInternalErrorTopic, jsonMessage);
            log.info("AsyncInternalServerErrorEvent Kafka 메시지 전송: {}", jsonMessage);
        } catch (JsonProcessingException e) {
            log.error("JSON 직렬화 오류: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Kafka 전송 오류: {}", e.getMessage());
        }
    }

    private record SyncMessage(
            LocalDateTime errorTime,
            Modules modules,
            Throwable throwable
    ) { }

    private record AsyncMessage(
            LocalDateTime errorTime,
            Modules modules,
            Throwable throwable
    ) { }
}
