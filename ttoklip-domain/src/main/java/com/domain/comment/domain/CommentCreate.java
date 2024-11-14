package com.domain.comment.domain;

import com.common.base.Filterable;

public record CommentCreate(String comment, Long parentCommentId) implements Filterable {

    @Override
    public String getFilterContent() {
        return comment();
    }
}
