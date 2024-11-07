package com.api.ttoklip.domain.notification.event;

import com.api.ttoklip.domain.common.comment.Comment;

public record CommentCreatedEvent(Comment comment) {

    public static CommentCreatedEvent from(Comment comment) {
        return new CommentCreatedEvent(comment);
    }
}
