package com.domain.notification.application;

import com.domain.notification.dto.request.NotificationRequest;
import com.domain.notification.event.CommentEvent;
import com.domain.notification.event.PostEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationListener {
    private final NotificationDispatcher notificationDispatcher;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void processCommentNotification(final CommentEvent event) {
        log.info("----- NotificationService.processCommentNotification call");
        notificationDispatcher.dispatchCommentNotification(
                event.comment(), event.notiCategory(), event.fromMemberId()
        );
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void processPostNotification(final PostEvent event) {
        log.info("----- NotificationService.processPostNotification call");

        notificationDispatcher.dispatchNotification(
                NotificationRequest.of(event.postId(), event.notiCategory(), event.fromMemberId())
        );
    }

}
