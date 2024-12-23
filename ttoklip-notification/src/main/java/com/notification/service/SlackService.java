package com.notification.service;

import com.notification.consumer.ErrorMessage;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SlackService {

    @Value("${slack.webhook.url}")
    private String slackWebhookUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String SYNC_TITLE = "\uD83D\uDEA8:warning: 동기화 에러가 발생했습니다! 즉시 확인 필요 :warning:\uD83D\uDEA8";
    private static final DateTimeFormatter KST_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Seoul"));

    public void sendErrorMessage(final ErrorMessage errorMessage) {
        try {
            if (errorMessage.isSyncError()) {
                log.info("동기화 에러 발생: {}", errorMessage);
                sendSync(errorMessage);
            } else {
                log.info("일반 에러 발생: {}", errorMessage);
                sendAsync(errorMessage);
            }
        } catch (Exception e) {
            log.error("Slack 알림 전송 실패: {}", e.getMessage(), e);
        }
    }

    private void sendSync(final ErrorMessage errorMessage) {
        Map<String, Object> payload = buildBaseMessage(errorMessage);

        List<Map<String, Object>> blocks = (List<Map<String, Object>>) payload.get("blocks");

        blocks.add(Map.of(
                "type", "section",
                "text", Map.of(
                        "type", "mrkdwn",
                        "text", SYNC_TITLE
                )
        ));

        restTemplate.postForObject(slackWebhookUrl, payload, String.class);
        log.info("Slack 동기화 에러 알림 전송 성공: {}", errorMessage);
    }

    private void sendAsync(final ErrorMessage errorMessage) {
        Map<String, Object> payload = buildBaseMessage(errorMessage);
        restTemplate.postForObject(slackWebhookUrl, payload, String.class);
        log.info("Slack 일반 에러 알림 전송 성공: {}", errorMessage);
    }

    private Map<String, Object> buildBaseMessage(final ErrorMessage errorMessage) {
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
                                        "- 동기 에러 여부: `%s`" +
                                        "- 에러 메시지: `%s`\n",
                                KST_FORMATTER.format(errorMessage.errorTime()),
                                errorMessage.modules(),
                                errorMessage.isSyncError() ? "예" : "아니요",
                                errorMessage.throwableMessage()
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

    private String truncateStackTrace(final String stackTrace) {
        int maxLength = 1000;
        return stackTrace.length() > maxLength ? stackTrace.substring(0, maxLength) + "..." : stackTrace;
    }
}
