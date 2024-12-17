package com.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackNotificationService {

    @Value("${slack.webhook.url}")
    private String slackWebhookUrl;

    public void sendErrorMessageToSlack(String message) {
        RestTemplate restTemplate = new RestTemplate();
        String payload = "{\"text\": \"" + message + "\"}";
        restTemplate.postForEntity(slackWebhookUrl, payload, String.class);
        log.info("Slack notification sent error message");
    }

//    // ToDo 추후 구현
//    public void sendErrorMessage(final ErrorMessage errorMessage) {
//        String payload = "{\"text\": \"" + errorMessage + "\"}";
//        restTemplate.postForEntity(slackWebhookUrl, payload, String.class);
//        log.info("Slack notification sent error message");
//    }
}

