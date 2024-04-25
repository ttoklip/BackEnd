package com.api.ttoklip.domain.notification.event;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.notification.dto.request.NotificationRequest;
import com.api.ttoklip.domain.notification.service.NotificationDispatcher;
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
    public void processCommentNotification(final CommentCreatedEvent event) {
        log.info("----- NotificationService.processCommentNotification call");
        Comment comment = event.getComment();
        notificationDispatcher.dispatchCommentNotification(comment);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void processPostNotification(final PostETCEvent event) {
        log.info("----- NotificationService.processPostNotification call");

        notificationDispatcher.dispatchNotification(
                NotificationRequest.of(event.getPostId(), event.getClassName(), event.getMethodName())
        );
    }

}
