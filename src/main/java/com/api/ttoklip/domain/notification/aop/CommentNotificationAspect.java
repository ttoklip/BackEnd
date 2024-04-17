package com.api.ttoklip.domain.notification.aop;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.notification.service.NotificationDispatcher;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CommentNotificationAspect {

    private final NotificationDispatcher notificationDispatcher;

    @AfterReturning(pointcut = "execution(* com.api.ttoklip.domain.common.comment.service.CommentService.register())")
    public void handleCommentNotification(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Comment comment = (Comment) args[0];
        notificationDispatcher.dispatchCommentNotification(comment);
    }
}
