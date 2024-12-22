package com.notification.service;

import com.notification.consumer.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SlackService {

    @Value("${slack.webhook.url}")
    private String slackWebhookUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendErrorMessage(final ErrorMessage errorMessage) {
        try {
            if (errorMessage.isSyncError()) {
                log.warn("동기화 에러 발생: {}", errorMessage);
                sendSyncErrorMessageToSlack(errorMessage);
            } else {
                log.info("일반 에러 발생: {}", errorMessage);
                sendRegularErrorMessageToSlack(errorMessage);
            }
        } catch (Exception e) {
            log.error("Slack 알림 전송 실패: {}", e.getMessage(), e);
        }
    }

    private void sendSyncErrorMessageToSlack(ErrorMessage errorMessage) {
        Map<String, Object> payload = buildSlackMessageWithBlocks(errorMessage);

        List<Map<String, Object>> blocks = (List<Map<String, Object>>) payload.get("blocks");
        blocks.add(0, Map.of(
                "type", "section",
                "text", Map.of(
                        "type", "mrkdwn",
                        "text", "\uD83D\uDEA8:warning: 동기화 에러가 발생했습니다! 즉시 확인 필요 :warning:\uD83D\uDEA8"
                )
        ));

        restTemplate.postForObject(slackWebhookUrl, payload, String.class);
        log.warn("Slack 동기화 에러 알림 전송 성공: {}", errorMessage);
    }

    private void sendRegularErrorMessageToSlack(ErrorMessage errorMessage) {
        Map<String, Object> payload = buildSlackMessageWithBlocks(errorMessage);
        restTemplate.postForObject(slackWebhookUrl, payload, String.class);
        log.info("Slack 일반 에러 알림 전송 성공: {}", errorMessage);
    }

    private Map<String, Object> buildSlackMessageWithBlocks(ErrorMessage errorMessage) {
        Map<String, Object> payload = new HashMap<>();
        List<Map<String, Object>> blocks = new ArrayList<>();

        blocks.add(Map.of(
                "type", "section",
                "text", Map.of(
                        "type", "mrkdwn",
                        "text", String.format(
                                "*[예외 알림]*\n" +
                                        "- 발생 시간: `%s`\n" +
                                        "- 모듈: `%s`\n" +
                                        "- 에러 메시지: `%s`\n" +
                                        "- 동기화 에러 여부: `%s`",
                                errorMessage.errorTime(),
                                errorMessage.modules(),
                                errorMessage.throwableMessage(),
                                errorMessage.isSyncError() ? "예" : "아니요"
                        )
                )
        ));

        blocks.add(Map.of(
                "type", "section",
                "text", Map.of(
                        "type", "mrkdwn",
                        "text", "```" + truncateStackTrace(errorMessage.stackTrace()) + "```"
                )
        ));

        payload.put("blocks", blocks);
        return payload;
    }

    private String truncateStackTrace(String stackTrace) {
        int maxLength = 1000;
        return stackTrace.length() > maxLength ? stackTrace.substring(0, maxLength) + "..." : stackTrace;
    }
}
