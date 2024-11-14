package com.api.notification.aspect;

import com.api.global.util.SecurityUtil;
import com.common.config.event.Events;
import com.domain.common.base.Identifiable;
import com.common.annotation.SendNotification;
import com.common.NotiCategory;
import com.domain.notification.event.PostEvent;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 3)
public class NotificationAspect {

    @AfterReturning("@annotation(com.common.annotation.SendNotification)")
    public void afterScrapNotification(JoinPoint joinPoint) {
        SendNotification notification = getSendNotification(joinPoint);

        log.info("[Notification] {}", joinPoint.getSignature());

        NotiCategory notiCategory = notification.notiCategory();
        Object argumentObj = joinPoint.getArgs()[0];

        Long fromMemberId = SecurityUtil.getCurrentMember().getId();

        if (argumentObj instanceof Identifiable) {
            Long targetIndex = ((Identifiable) argumentObj).getId();

            Events.raise(PostEvent.of(targetIndex, notiCategory, fromMemberId));
        }
    }

    private SendNotification getSendNotification(final JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return method.getAnnotation(SendNotification.class);
    }
}