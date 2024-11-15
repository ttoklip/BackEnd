package com.domain.comment.domain;

import com.common.base.Filterable;

public record CommentCreate(String comment, Long parentCommentId) implements Filterable {

    public static CommentCreate of(String comment, Long parentCommentId) {
        return new CommentCreate(comment, parentCommentId);
    }

    @Override
    public String getFilterContent() {
        return comment();
    }
}
