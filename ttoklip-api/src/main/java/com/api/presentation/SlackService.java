package com.api.presentation;

import com.common.event.Modules;
import com.slack.api.Slack;
import com.slack.api.webhook.WebhookPayloads;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackService {

    @Value("${slack.webhook.url}")
    private String slackWebhookUrl;

    private final Slack slackClient = Slack.getInstance();

    public void sendErrorMessage(String title, Throwable e, Modules module, int statusCode) {
        try {
            String message = String.format(
                    "*🚨 예외 발생 알림*\n" +
                            "*에러 모듈*: %s\n" +
                            "*HTTP 상태 코드*: %d\n" +
                            "*에러 메시지*: %s\n" +
                            "*에러 클래스*: %s\n" +
                            "*발생 시각*: %s",
                    module.name(),
                    statusCode,
                    e.getMessage(),
                    e.getClass().getName(),
                    LocalDateTime.now()
            );

            slackClient.send(slackWebhookUrl, WebhookPayloads.payload(p -> p.text(message)));
        } catch (IOException ex) {
            log.error("Slack 알림 전송 실패: {}", ex.getMessage(), ex);
        }
    }
}
