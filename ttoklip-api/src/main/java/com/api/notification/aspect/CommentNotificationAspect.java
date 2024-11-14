package com.api.notification.aspect;

import com.api.global.util.SecurityUtil;
import com.common.config.event.Events;
import com.domain.comment.domain.Comment;
import com.domain.notification.domain.annotation.SendCommentNotification;
import com.domain.notification.domain.vo.NotiCategory;
import com.domain.notification.event.CommentEvent;
import java.util.Arrays;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class CommentNotificationAspect {

    @AfterReturning(value = "@annotation(commentNotification)")
    public void afterScrapNotification(JoinPoint joinPoint, SendCommentNotification commentNotification) {
        Optional<Comment> commentOptional = Arrays.stream(joinPoint.getArgs())
                .filter(Comment.class::isInstance)
                .map(Comment.class::cast)
                .findFirst();

        Long fromMemberId = SecurityUtil.getCurrentMember().getId();

        commentOptional.ifPresent(comment -> {
            NotiCategory notiCategory = commentNotification.notiCategory();
            log.info("[Notification] {} - Notification Type: {}", joinPoint.getSignature(), notiCategory);

            Events.raise(new CommentEvent(comment, notiCategory, fromMemberId));
        });
    }
}

