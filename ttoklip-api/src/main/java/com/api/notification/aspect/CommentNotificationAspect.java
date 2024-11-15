package com.api.notification.aspect;

import com.api.global.util.SecurityUtil;
import com.common.NotiCategory;
import com.common.annotation.SendCommentNotification;
import com.common.config.event.Events;
import com.domain.comment.domain.Comment;
import com.domain.notification.event.CommentEvent;
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
@Order(Ordered.HIGHEST_PRECEDENCE + 4)
public class CommentNotificationAspect {

    @AfterReturning(value = "@annotation(com.common.annotation.SendCommentNotification)", returning = "result")
    public void afterScrapNotification(JoinPoint joinPoint, Object result) {
        SendCommentNotification sendNotification = getSendCommentNotification(joinPoint);

        if (result instanceof Comment comment) {
            Long fromMemberId = SecurityUtil.getCurrentMember().getId();

            log.info("Returned Comment: {}", comment);

            NotiCategory notiCategory = sendNotification.notiCategory();
            log.info("[Notification] {} - Notification Type: {}", joinPoint.getSignature(), notiCategory);

            Events.raise(new CommentEvent(comment, notiCategory, fromMemberId));
        }
    }


    private SendCommentNotification getSendCommentNotification(final JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return method.getAnnotation(SendCommentNotification.class);
    }
}

