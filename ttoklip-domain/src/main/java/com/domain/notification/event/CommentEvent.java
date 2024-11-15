package com.domain.notification.event;

import com.domain.comment.domain.Comment;
import com.common.NotiCategory;

public record CommentEvent(Comment comment, NotiCategory notiCategory, Long fromMemberId) {

    public static CommentEvent of(Comment comment, NotiCategory notiCategory, Long fromMemberId) {
        return new CommentEvent(comment, notiCategory, fromMemberId);
    }
}
