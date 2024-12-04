package com.domain.exception.application;

import com.common.event.AsyncInternalServerExceptionEvent;
import com.common.event.InternalServerExceptionEvent;
import com.common.event.ExceptionEvent;  // 상위 클래스
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InternalErrorProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.internal.error}")
    private String internalErrorTopic;

    @EventListener({InternalServerExceptionEvent.class, AsyncInternalServerExceptionEvent.class})
    public void handleInternalServerErrorEvent(ExceptionEvent event) {
        try {
            boolean isAsync = event instanceof AsyncInternalServerExceptionEvent;

            ErrorMessage errorMessage = new ErrorMessage(
                    event.getErrorTime(),
                    event.getModules(),
                    event.getThrowable().getMessage(),
                    Arrays.toString(event.getThrowable().getStackTrace()),
                    isAsync
            );

            String jsonMessage = objectMapper.writeValueAsString(errorMessage);
            kafkaTemplate.send(internalErrorTopic, jsonMessage);
            log.info("ExceptionEvent Kafka 메시지 전송: {}", jsonMessage);
        } catch (JsonProcessingException e) {
            log.error("JSON 직렬화 오류: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Kafka 전송 오류: {}", e.getMessage());
        }
    }
}
