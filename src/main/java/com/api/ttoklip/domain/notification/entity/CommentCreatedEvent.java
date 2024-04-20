package com.api.ttoklip.domain.notification.entity;

import com.api.ttoklip.domain.common.comment.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CommentCreatedEvent {

    private final Comment comment;

}
