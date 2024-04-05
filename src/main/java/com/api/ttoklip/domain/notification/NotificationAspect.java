package com.api.ttoklip.domain.notification;

import static com.api.ttoklip.global.success.Message.SCRAP;

import com.api.ttoklip.domain.notification.annotation.SendNotification;
import com.api.ttoklip.global.success.Message;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class NotificationAspect {

    @AfterReturning(value = "@annotation(notification)", returning = "message")
    public void afterScrapNotification(JoinPoint joinPoint, SendNotification notification, Message message) {

        log.info("[Notification] {}", joinPoint.getSignature());
        // Scrap 알림 발송
        if (message != null && message.getMessage().contains(SCRAP)) {
            sendHoneyTipScrap(message.getMessage());
        }

    }

    // ToDo 추후 수정
    private void sendHoneyTipScrap(final String message) {
    }

    // ToDo FCM 기능 완성
    private void sendFCM() {
    }
}
