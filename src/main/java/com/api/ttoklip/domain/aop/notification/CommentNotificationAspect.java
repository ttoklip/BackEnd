package com.api.ttoklip.domain.aop.notification;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.notification.event.CommentCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CommentNotificationAspect {

    private final ApplicationEventPublisher eventPublisher;

    @Pointcut("execution(* com.api.ttoklip.domain.common.comment.service.CommentService.register(com.api.ttoklip.domain.common.comment.Comment))")
    private void commentPointCut() {
    }

    @AfterReturning(pointcut = "commentPointCut() && args(comment)")
    public void handleCommentNotification(Comment comment) {
//        notificationDispatcher.dispatchCommentNotification(comment);
        eventPublisher.publishEvent(new CommentCreatedEvent(comment));
        log.info("----- eventPublisher publishEvent method call");
    }
}
