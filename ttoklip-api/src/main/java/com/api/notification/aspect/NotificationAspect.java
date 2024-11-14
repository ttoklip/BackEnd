package com.api.notification.aspect;

import com.api.global.util.SecurityUtil;
import com.common.config.event.Events;
import com.domain.common.base.Identifiable;
import com.domain.notification.domain.annotation.SendNotification;
import com.domain.notification.domain.vo.NotiCategory;
import com.domain.notification.event.PostEvent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class NotificationAspect {

    @AfterReturning(value = "@annotation(notification)")
    public void afterScrapNotification(JoinPoint joinPoint, SendNotification notification) {
        log.info("[Notification] {}", joinPoint.getSignature());

        NotiCategory notiCategory = notification.notiCategory();
        Object argumentObj = joinPoint.getArgs()[0];

        Long fromMemberId = SecurityUtil.getCurrentMember().getId();

        if (argumentObj instanceof Identifiable) {
            Long targetIndex = ((Identifiable) argumentObj).getId();

            Events.raise(PostEvent.of(targetIndex, notiCategory, fromMemberId));
        }
    }
}
